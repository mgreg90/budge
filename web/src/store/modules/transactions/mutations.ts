import { Payload } from "@/types/store.types";
import { MutationTree } from "vuex";
import { TransactionsState } from "./index";

export enum MutationTypes {
  SET_MESSAGE = "SET_MESSAGE",
}

export type Mutations<S = TransactionsState> = {
  [MutationTypes.SET_MESSAGE](state: S, payload: Payload<string>): void;
};

export const mutations: MutationTree<TransactionsState> & Mutations = {
  [MutationTypes.SET_MESSAGE](state, payload) {
    state.message = payload.value;
  },
};
