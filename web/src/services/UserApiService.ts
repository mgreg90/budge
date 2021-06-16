import { ApiErrorResponse } from "@/models/ApiErrorResponse";
import { Either } from "@/models/Either";
import Session from "@/models/Session";
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
  ): Promise<Either<Session | null, ApiErrorResponse | null>> {
    const response = await this.baseApiService.post({ body, skipAuth: true });

    if (response.isFailure()) {
      console.error("API Request Failed!", response.exception);
      return Promise.resolve(new Either(null, response.exception));
    }

    let responseJson: IUserCreateResponseDto;
    try {
      responseJson = await response.value!.json<IUserCreateResponseDto>();
    } catch (exception) {
      console.error("Failed to parse json!", exception);
      return Promise.resolve(new Either(null, new ApiErrorResponse()));
    }

    return Promise.resolve(new Either(Session.fromResponseDto(responseJson)));
  }
}

export default UserApiService;
