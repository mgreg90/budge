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

interface TransactionsState {
  message: string;
}

const defaultMsg = "Private data not loaded";

const state: TransactionsState = {
  message: defaultMsg,
};

const transactionsModule: Module<TransactionsState, RootState> = {
  state,
  mutations,
  actions,
  getters,
};

export { TransactionsState, ActionTypes, Store, defaultMsg };
export default transactionsModule;

type Store<TransactionsState> = Omit<
  VuexStore<TransactionsState>,
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
