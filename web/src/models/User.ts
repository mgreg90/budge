import { IUserResponse } from "@/services/UserApiService";

export default class User {
  id: string; // TODO eventually create a UUID class to validate UUIDs
  token: string;

  static fromJson(json: IUserResponse) {
    return new User(json.id, json.token);
  }

  constructor(id: string, token: string) {
    this.id = id;
    this.token = token;
  }
}
