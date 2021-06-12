import { ApiErrorResponse } from "@/models/ApiErrorResponse";
import { Either } from "@/models/Either";
import User from "@/models/User";
import { IUserCreateRequestDto } from "@/types/apiRequests.types";
import { IUserCreateResponseDto } from "@/types/apiResponses.types";
import BaseApiService from "./BaseApiService";

class UserApiService {
  private resourcePath = "users";
  private baseApiService: BaseApiService;

  constructor() {
    this.baseApiService = new BaseApiService(this.resourcePath);
  }

  async create(
    body: IUserCreateRequestDto
  ): Promise<Either<User | null, ApiErrorResponse | null>> {
    const response = await this.baseApiService.post(body);

    if (response.isFailure()) {
      console.error(response.exception);
      return new Either(null, response.exception);
    }

    let responseJson: IUserCreateResponseDto;
    try {
      responseJson = await response.value!.json<IUserCreateResponseDto>();
    } catch (exception) {
      console.error("Error parsing json!", exception);
      return Promise.resolve(new Either(null, new ApiErrorResponse()));
    }

    const user = User.fromJson(responseJson);

    return new Either(user, null);
  }
}

export default UserApiService;

export interface IUserResponse {
  id: string;
  token: string;
}
