import User from "@/models/User";
import { userApiService } from "@/services";
import { IUserStoreState, Payload } from "@/types/store.types";
import { Module } from "vuex";

const initialState: IUserStoreState = {
  currentUser: undefined,
  isLoading: false,
  errorMessages: [],
};

const mutations = {
  setCurrentUser(state: IUserStoreState, payload: Payload<User>) {
    state.currentUser = payload.value;
  },
  setIsLoading(state: IUserStoreState, payload: Payload<boolean>) {
    state.isLoading = payload.value;
  },
  setErrorMessages(state: IUserStoreState, payload: Payload<string[]>) {
    state.errorMessages = payload.value;
  },
};

const actions = {
  async signup(context: any, model: any) {
    debugger;
    context.commit("setIsLoading", { value: true });
    const either = await userApiService.create(model);

    if (either.isSuccess()) {
      console.log("success", either.value);
      context.commit("setCurrentUser", { value: either.value });
    } else {
      console.log("failure", either.exception, either.exception?.messages());
      context.commit("setErrorMessages", {
        value: either.exception?.messages(),
      });
    }

    context.commit("setIsLoading", { value: false });
  },
};

const getters = {
  isLoading: (state: IUserStoreState) => state.isLoading,
  errorMessages: (state: IUserStoreState) => state.errorMessages,
};

const userStore: Module<IUserStoreState, any> = {
  state: () => initialState,
  mutations,
  actions,
  getters,
};

export default userStore;
