import { InjectionKey } from "vue";
import {
  createStore,
  Store as VuexStore,
  useStore as baseUseStore,
} from "vuex";
import auth, {
  AuthState as AuthState,
  Store as AuthStore,
} from "./modules/auth";

export interface RootState {
  auth: AuthState;
}

export type RootStore = AuthStore<Pick<RootState, "auth">>;

export const key: InjectionKey<VuexStore<RootState>> = Symbol();

export const store = createStore<RootState>({
  modules: {
    auth,
  },
});

export const useStore: () => RootStore = () => baseUseStore(key);
