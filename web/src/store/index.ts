import { InjectionKey } from "vue";
import {
  createStore,
  Store as VuexStore,
  useStore as baseUseStore,
} from "vuex";
import auth, { AuthState, Store as AuthStore } from "./modules/auth";
import transactions, {
  TransactionsState,
  Store as TransactionsStore,
} from "./modules/transactions";

export interface RootState {
  auth: AuthState;
  transactions: TransactionsState;
}

export type RootStore = AuthStore<Pick<RootState, "auth">> &
  TransactionsStore<Pick<RootState, "transactions">>;

export const key: InjectionKey<VuexStore<RootState>> = Symbol();

export const store = createStore<RootState>({
  modules: {
    auth,
    transactions,
  },
});

export const useStore: () => RootStore = () => baseUseStore(key);
