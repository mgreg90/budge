import urljoin from "url-join";
import { Either } from "@/models/Either";
import { ResponseWrapper } from "@/models/ResponseWrapper";
import { ApiErrorResponse } from "@/models/ApiErrorResponse";

class BaseApiService {
  private path: string;

  private baseUrl = "http://localhost:3000/api/v1"; // TODO read from env var
  private defaultHeaders = {
    "Access-Control-Allow-Origin": "*", // TODO read from env var
  };

  constructor(path: string) {
    this.path = path;
  }

  async post(
    body: any
  ): Promise<Either<ResponseWrapper | null, ApiErrorResponse | null>> {
    let response: Response;
    try {
      response = await fetch(this.url(), {
        method: "POST",
        headers: this.defaultHeaders,
        body: JSON.stringify(body),
      });
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

  private url(): string {
    return urljoin(this.baseUrl, this.path);
  }
}

export default BaseApiService;
