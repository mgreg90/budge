import User from "@/models/User";
import { JwtPayload } from "jwt-decode";

export interface ISession {
  user: IUser;
  token: string;
}

export interface IUser {
  id: string;
  email: string;
}

export interface ISession {
  user: User;
  token: string;
}

export const LOCAL_STORAGE_SESSION_KEY = "userSession";

export interface JwtTokenBody extends JwtPayload {
  userId: string;
  email: string;
}
