import { RootState } from "@/store";
import { GetterTree } from "vuex";
import { TransactionsState } from ".";

export type Getters<S = TransactionsState> = {
  message: (state: S) => string;
};

export const getters: GetterTree<TransactionsState, RootState> &
  Getters<TransactionsState> = {
  message: (state: TransactionsState): string => state.message,
};
