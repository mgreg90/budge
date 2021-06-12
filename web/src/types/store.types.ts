import User from "@/models/User";

export interface Payload<T> {
  value: T;
}


// export interface IStore {
//   state(): () => IStoreState;
//   mutations: IStoreMutations;
//   actions: IStoreActions;
//   getters: IStoreGetters;
// }

// export type IStoreState = IUserStoreState | IOtherStoreState; // add on to this
// export type IStoreMutations = IUserStoreMutations; // add on to this
// export type IStoreActions = IUserStoreActions; // add on to this
// export type IStoreGetters = IUserStoreGetters; // add on to this

// User Store

export interface IUserStoreState {
  currentUser?: User;
  isLoading: boolean;
  errorMessages: string[];
}

// export interface IUserStoreMutations {
//   setCurrentUser: (state: IUserStoreState, payload: Payload<User>) => void;
//   setIsLoading: (state: IUserStoreState, payload: Payload<boolean>) => void;
//   setErrorMessages: (
//     state: IUserStoreState,
//     payload: Payload<string[]>
//   ) => void;
// }

// export interface IUserStoreActions {
//   signup: (context: any, model: any) => void;
// }

// export interface IUserStoreGetters {
//   isLoading: (state: IUserStoreState) => boolean;
//   errorMessages: (state: IUserStoreState) => string[];
// }

// // User Store - End

// // OtherStoreState - Just for testing things out - delete later
// export interface IOtherStoreState {
  
// }