import {
  IApiErrorResponse,
  IApiErrorResponseResults,
} from "@/types/apiResponses.types";

const AUTH_ERROR_TYPE = "budge.errors.authentication-failed";

export class ApiErrorResponse {
  private response?: IApiErrorResponse;

  constructor(response?: IApiErrorResponse) {
    this.response = response;
  }

  messages(): string[] {
    if (this.response?.type === AUTH_ERROR_TYPE)
      return ["Authentication Failed!"];

    const messages = this.response?.results?.map(
      (result: IApiErrorResponseResults) => result.message
    );
    return messages ?? ["Something went wrong!"];
  }
}
