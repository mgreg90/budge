import { getters, Getters } from "./getters";
import { mutations, Mutations } from "./mutations";
import { actions, Actions, ActionTypes } from "./actions";
import {
  Store as VuexStore,
  Module,
  CommitOptions,
  DispatchOptions,
} from "vuex";
import { RootState } from "@/store";
import Session from "@/models/Session";

interface AuthState {
  session?: Session;
  isLoading: boolean;
  loginErrorMessages: string[];
  signupErrorMessages: string[];
}

const state: AuthState = {
  session: undefined,
  isLoading: false,
  loginErrorMessages: [],
  signupErrorMessages: [],
};

const authModule: Module<AuthState, RootState> = {
  state,
  mutations,
  actions,
  getters,
};

export { AuthState, ActionTypes, Store };
export default authModule;

type Store<AuthState> = Omit<
  VuexStore<AuthState>,
  "commit" | "getters" | "dispatch"
> & {
  commit<K extends keyof Mutations, P extends Parameters<Mutations[K]>[1]>(
    key: K,
    payload: P,
    options?: CommitOptions
  ): ReturnType<Mutations[K]>;
} & {
  getters: {
    [K in keyof Getters]: ReturnType<Getters[K]>;
  };
} & {
  dispatch<K extends keyof Actions>(
    key: K,
    payload?: Parameters<Actions[K]>[1],
    options?: DispatchOptions
  ): ReturnType<Actions[K]>;
};
