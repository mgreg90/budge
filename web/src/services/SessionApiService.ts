import { ApiErrorResponse } from "@/models/ApiErrorResponse";
import { Either } from "@/models/Either";
import Session from "@/models/Session";
import { ISessionCreateRequestDto } from "@/types/apiRequests.types";
import {
  ISessionCreateResponseDto,
  ISessionReadResponseDto,
} from "@/types/apiResponses.types";
import BaseApiService from "./BaseApiService";

class SessionApiService {
  private resourcePath = "sessions";
  private baseApiService: BaseApiService;

  constructor() {
    this.baseApiService = new BaseApiService(this.resourcePath);
  }

  async login(
    body: ISessionCreateRequestDto
  ): Promise<Either<Session | null, ApiErrorResponse | null>> {
    const response = await this.baseApiService.post({ body, skipAuth: true });

    if (response.isFailure()) {
      console.error("API Request Failed!", response.exception);
      return Promise.resolve(new Either(null, response.exception));
    }

    let responseBody: ISessionCreateResponseDto;
    try {
      responseBody = await response.value!.json<ISessionCreateResponseDto>();
    } catch (e) {
      console.error("Failed to parse response!", e);
      return Promise.resolve(new Either(null, e));
    }

    return Promise.resolve(new Either(Session.fromResponseDto(responseBody)));
  }

  async validate(): Promise<boolean> {
    try {
      const response = await this.baseApiService.get();

      if (response.isFailure()) {
        console.error("Failed to validate token!", response.exception);
        return false;
      }

      let responseBody: ISessionReadResponseDto;
      try {
        responseBody = await response.value!.json<ISessionReadResponseDto>();
        return responseBody.isTokenValid;
      } catch (e) {
        console.error("Failed to parse response!", e);
      }
      return false;
    } catch (e) {
      return false;
    }
  }
}

export default SessionApiService;
