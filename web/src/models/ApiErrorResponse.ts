import {
  IApiErrorResponse,
  IApiErrorResponseResults,
} from "@/types/apiResponses.types";

export class ApiErrorResponse {
  private response?: IApiErrorResponse;

  constructor(response?: IApiErrorResponse) {
    this.response = response;
  }

  messages(): string[] {
    const messages = this.response?.results?.map(
      (result: IApiErrorResponseResults) => result.message
    );
    return messages ?? ["Something went wrong!"];
  }
}
