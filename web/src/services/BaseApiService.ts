import urljoin from "url-join";
import { Either } from "@/models/Either";
import { ResponseWrapper } from "@/models/ResponseWrapper";
import { ApiErrorResponse } from "@/models/ApiErrorResponse";
import Session from "@/models/Session";
import { IRequestOptions } from "@/types/apiRequests.types";

enum HttpRequestMethod {
  GET = "GET",
  POST = "POST",
  PUT = "PUT",
  PATCH = "PATCH",
  DELTE = "DELETE",
}

class BaseApiService {
  private path: string;

  private baseUrl = "http://localhost:3000/api/v1"; // TODO read from env var
  private defaultHeaders = {
    "Access-Control-Allow-Origin": "*", // TODO read from env var
  };

  constructor(path: string) {
    this.path = path;
  }

  async get(
    options: IRequestOptions = {}
  ): Promise<Either<ResponseWrapper | null, ApiErrorResponse | null>> {
    return await this.executeRequest(HttpRequestMethod.GET, options);
  }

  async post(
    options: IRequestOptions = {}
  ): Promise<Either<ResponseWrapper | null, ApiErrorResponse | null>> {
    return await this.executeRequest(HttpRequestMethod.POST, options);
  }

  private async executeRequest(
    httpMethod: HttpRequestMethod,
    options: IRequestOptions
  ): Promise<Either<ResponseWrapper | null, ApiErrorResponse | null>> {
    const [url, fetchOptions] = this.buildRequest(httpMethod, options);

    let response: Response;
    try {
      response = await fetch(url.toString(), fetchOptions);
    } catch (exception) {
      console.error(exception);
      return new Either(null, new ApiErrorResponse());
    }

    const wrappedResponse = new ResponseWrapper(response);

    if (wrappedResponse.isSuccess()) {
      return new Either(wrappedResponse, null);
    }

    let apiErrorResponse: ApiErrorResponse = new ApiErrorResponse();
    try {
      apiErrorResponse = await wrappedResponse.toApiErrorResponse();
    } catch (e) {
      console.error(e);
    }

    return new Either(null, apiErrorResponse);
  }

  private buildRequest(
    httpMethod: HttpRequestMethod,
    options: IRequestOptions
  ): [string, RequestInit] {
    const { body, skipAuth = false } = options;
    const headers = { ...this.defaultHeaders, ...this.authHeaders(skipAuth) };
    const fetchOptions: RequestInit = {
      method: httpMethod,
      headers: headers,
    };
    const url = new URL(this.url());

    switch (httpMethod) {
      case HttpRequestMethod.GET:
        if (!body) break;
        url.search = new URLSearchParams(body).toString();
        break;
      case HttpRequestMethod.POST:
        if (body) fetchOptions.body = JSON.stringify(body);
        break;
    }

    return [url.toString(), fetchOptions];
  }

  private authHeaders(skipAuth: boolean): Record<string, string> {
    if (skipAuth) return {};

    return { Authorization: `Bearer ${Session.fromToken().token}` };
  }

  private url(): string {
    return urljoin(this.baseUrl, this.path);
  }
}

export default BaseApiService;
