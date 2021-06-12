import { IStore, IStoreState } from "@/types/store.types";
import { InjectionKey } from "vue";
import { createStore, Store, useStore as baseUseStore } from "vuex";
import userStore from "./userStore";

export const storeKey: InjectionKey<Store<IStore>> = Symbol();

export const store = createStore<IStore>({
  modules: {
    user: userStore,
  },
});

export const useStore = () => baseUseStore(storeKey);
