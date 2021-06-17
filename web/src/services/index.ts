import SessionApiService from "./SessionApiService";
import TransactionsApiService from "./TransactionsApiService";
import UserApiService from "./UserApiService";

export const sessionApiService = new SessionApiService();
export const userApiService = new UserApiService();
export const transactionsApiService = new TransactionsApiService();
