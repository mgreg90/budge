export interface Payload<T> {
  value: T;
}

export type AugmentedActionContext = {
  commit<K extends keyof Mutations>(
    key: K,
    payload: Parameters<Mutations[K]>[1]
  ): ReturnType<Mutations[K]>;
} & Omit<ActionContext<AuthState, RootState>, "commit">;

type Commit = <K extends keyof Mutations<AuthState>>(
  key: K,
  payload: Parameters<Mutations<AuthState>[K]>[1]
) => ReturnType<Mutations<AuthState>[K]>;
