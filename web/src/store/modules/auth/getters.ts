import { GetterTree } from "vuex";
import { AuthState } from "./index";
import { RootState } from "@/store";
import Session from "@/models/Session";

export type Getters<S = AuthState> = {
  isLoading: (state: S) => boolean;
  loginErrorMessages: (state: S) => string[];
  signupErrorMessages: (state: S) => string[];
  session: (state: S) => Session | null;
};

export const getters: GetterTree<AuthState, RootState> & Getters<AuthState> = {
  isLoading: (state: AuthState): boolean => state.isLoading,
  loginErrorMessages: (state: AuthState): string[] => state.loginErrorMessages,
  signupErrorMessages: (state: AuthState): string[] =>
    state.signupErrorMessages,
  session: (state: AuthState): Session | null => state.session,
};
