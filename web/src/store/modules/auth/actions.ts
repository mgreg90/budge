import { ActionContext, ActionTree } from "vuex";
import { Mutations, MutationTypes } from "./mutations";
import { AuthState } from "./index";
import { RootState } from "@/store";
import { sessionApiService, userApiService } from "@/services";
import {
  ISessionCreateRequestDto,
  IUserCreateRequestDto,
} from "@/types/apiRequests.types";
import { Either } from "@/models/Either";
import { ApiErrorResponse } from "@/models/ApiErrorResponse";
import Session from "@/models/Session";
import { JwtTokenBody, LOCAL_STORAGE_SESSION_KEY } from "@/types/domain.types";
import router from "@/router";

export enum ActionTypes {
  SIGNUP = "AUTH__SIGNUP",
  LOGIN = "AUTH__LOGIN",
  SET_SESSION = "SET_SESSION",
}

type AugmentedActionContext = {
  commit<K extends keyof Mutations>(
    key: K,
    payload: Parameters<Mutations[K]>[1]
  ): ReturnType<Mutations[K]>;
} & Omit<ActionContext<AuthState, RootState>, "commit">;

type Commit = <K extends keyof Mutations<AuthState>>(
  key: K,
  payload: Parameters<Mutations<AuthState>[K]>[1]
) => ReturnType<Mutations<AuthState>[K]>;

export interface Actions {
  [ActionTypes.SIGNUP](
    { commit }: AugmentedActionContext,
    payload: IUserCreateRequestDto
  ): void;
  [ActionTypes.LOGIN](
    { commit }: AugmentedActionContext,
    payload: ISessionCreateRequestDto
  ): void;
  [ActionTypes.SET_SESSION]({ commit }: AugmentedActionContext): void;
}

export const actions: ActionTree<AuthState, RootState> & Actions = {
  async [ActionTypes.SIGNUP]({ commit }, model: IUserCreateRequestDto) {
    setAuthFetchingState(commit);

    const either = await userApiService.create(model);

    handleAuthResponse(either, commit, MutationTypes.SET_SIGNUP_ERROR_MESSAGES);
  },

  async [ActionTypes.LOGIN]({ commit }, model: ISessionCreateRequestDto) {
    setAuthFetchingState(commit);

    const either = await sessionApiService.login(model);

    handleAuthResponse(either, commit, MutationTypes.SET_LOGIN_ERROR_MESSAGES);
  },

  async [ActionTypes.SET_SESSION]({ commit }) {
    let session: Session | null = null;
    try {
      session = Session.fromToken();
      if (!session) {
        console.log("No token found in localStorage");
        return false;
      }
      console.log("Token found in localStorage");
      const isValid = await sessionApiService.validate();
      if (isValid) {
        console.log("Creating session from token");
        commit(MutationTypes.SET_SESSION, { value: session });
        console.log("Session created in store");
        return true;
      }
      console.log("Failed to create session in store");
      router.push("/login");
    } catch (e) {
      console.error("Failed set session!", e, session);
      return false;
    }
  },
};

// Helpers
const setAuthFetchingState = (commit: Commit) => {
  commit(MutationTypes.SET_IS_LOADING, { value: true });
  commit(MutationTypes.SET_SESSION, { value: undefined });
};

const handleAuthResponse = (
  either: Either<Session | null, ApiErrorResponse | null>,
  commit: Commit,
  errMsgMutation: MutationTypes
) => {
  if (either.isSuccess()) {
    const session = either.value!;
    console.log("success", either.value);
    commit(MutationTypes.SET_SESSION, { value: either.value! });
    commit(errMsgMutation, { value: [] });
  } else {
    console.log("failure", either.exception, either.exception?.messages());
    commit(errMsgMutation, {
      value: either.exception!.messages()!,
    });
  }

  commit(MutationTypes.SET_IS_LOADING, { value: false });
};
