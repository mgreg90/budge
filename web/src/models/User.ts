import {
  ISessionCreateResponseDto,
  IUserCreateResponseDto,
} from "@/types/apiResponses.types";
import { IUser } from "@/types/domain.types";

export default class User implements IUser {
  id: string; // TODO eventually create a UUID class to validate UUIDs
  email: string;

  static fromJson(json: IUserCreateResponseDto | ISessionCreateResponseDto) {
    return new User(json.user.id, json.user.email);
  }

  constructor(id: string, email: string) {
    this.id = id;
    this.email = email;
  }
}
