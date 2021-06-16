import { IApiErrorResponse } from "@/types/apiResponses.types";
import { ApiErrorResponse } from "./ApiErrorResponse";

export class ResponseWrapper {
  private response: Response;

  constructor(response: Response) {
    this.response = response;
  }

  async json<T>(): Promise<T> {
    return (await this.response.json()) as T;
  }

  isSuccess(): boolean {
    return this.response.status >= 200 && this.response.status < 300;
  }

  isError(): boolean {
    return this.response.status >= 200 && this.response.status < 300;
  }

  isHandledError(): boolean {
    return this.response.status >= 400 && this.response.status < 500;
  }

  isUnhandledError(): boolean {
    return (
      this.response.status >= 500 ||
      (this.response.status >= 300 && this.response.status < 400)
    );
  }

  async toApiErrorResponse(): Promise<ApiErrorResponse> {
    let json: IApiErrorResponse;

    try {
      json = await this.response.json();
    } catch (exception) {
      return Promise.resolve(new ApiErrorResponse());
    }

    return Promise.resolve(new ApiErrorResponse(json));
  }
}
