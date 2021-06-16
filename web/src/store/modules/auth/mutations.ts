import Session from "@/models/Session";
import { Payload } from "@/types/store.types";
import { MutationTree } from "vuex";
import { AuthState } from "./index";

const localStorageSessionKey = "userSession";

export enum MutationTypes {
  SET_SESSION = "SET_SESSION",
  SET_IS_LOADING = "SET_IS_LOADING",
  SET_LOGIN_ERROR_MESSAGES = "SET_LOGIN_ERROR_MESSAGES",
  SET_SIGNUP_ERROR_MESSAGES = "SET_SIGNUP_ERROR_MESSAGES",
}

export type Mutations<S = AuthState> = {
  [MutationTypes.SET_SESSION](
    state: S,
    payload: Payload<Session | undefined>
  ): void;
  [MutationTypes.SET_IS_LOADING](state: S, payload: Payload<boolean>): void;
  [MutationTypes.SET_LOGIN_ERROR_MESSAGES](
    state: S,
    payload: Payload<string[]>
  ): void;
  [MutationTypes.SET_SIGNUP_ERROR_MESSAGES](
    state: S,
    payload: Payload<string[]>
  ): void;
};

export const mutations: MutationTree<AuthState> & Mutations = {
  [MutationTypes.SET_SESSION](state, payload) {
    state.session = payload.value;
    if (payload.value?.token)
      localStorage.setItem(localStorageSessionKey, payload.value.token);
    else localStorage.removeItem(localStorageSessionKey);
  },
  [MutationTypes.SET_IS_LOADING](state, payload) {
    state.isLoading = payload.value;
  },
  [MutationTypes.SET_LOGIN_ERROR_MESSAGES](state, payload) {
    state.loginErrorMessages = payload.value;
  },
  [MutationTypes.SET_SIGNUP_ERROR_MESSAGES](state, payload) {
    state.signupErrorMessages = payload.value;
  },
};
