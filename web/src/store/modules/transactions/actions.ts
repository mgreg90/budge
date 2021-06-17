import { ActionContext, ActionTree } from "vuex";
import { Mutations, MutationTypes } from "./mutations";
import { RootState } from "@/store";
import { defaultMsg, TransactionsState } from ".";
import { transactionsApiService } from "@/services";

export enum ActionTypes {
  GET = "TRANSACTIONS__GET",
}

export interface Actions {
  [ActionTypes.GET]({ commit }: AugmentedActionContext): void;
}

export type AugmentedActionContext = {
  commit<K extends keyof Mutations>(
    key: K,
    payload: Parameters<Mutations[K]>[1]
  ): ReturnType<Mutations[K]>;
} & Omit<ActionContext<TransactionsState, RootState>, "commit">;

type Commit = <K extends keyof Mutations<TransactionsState>>(
  key: K,
  payload: Parameters<Mutations<TransactionsState>[K]>[1]
) => ReturnType<Mutations<TransactionsState>[K]>;

export const actions: ActionTree<TransactionsState, RootState> & Actions = {
  async [ActionTypes.GET]({ commit }) {
    const either = await transactionsApiService.read();

    if (either.isSuccess()) {
      console.log("success", either.value);
      commit(MutationTypes.SET_MESSAGE, {
        value: either.value?.message || defaultMsg,
      });
    } else {
      console.log("failure", either.exception, either.exception?.messages());
      commit(MutationTypes.SET_MESSAGE, { value: defaultMsg });
    }
    // TODO Left off here
    // Make call to transactions endpoint and display data
    // Then work on making the endpoint protected
  },
};

// Helpers
