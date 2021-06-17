import { ApiErrorResponse } from "@/models/ApiErrorResponse";
import { Either } from "@/models/Either";
import { ITransactionsReadResponseDto } from "@/types/apiResponses.types";
import BaseApiService from "./BaseApiService";

class TransactionsApiService {
  private resourcePath = "transactions";
  private baseApiService: BaseApiService;

  constructor() {
    this.baseApiService = new BaseApiService(this.resourcePath);
  }

  async read(): Promise<
    Either<ITransactionsReadResponseDto | null, ApiErrorResponse | null>
  > {
    const response = await this.baseApiService.get();

    if (response.isFailure()) {
      console.error("API Request Failed!", response.exception);
      return Promise.resolve(new Either(null, response.exception));
    }

    let responseJson: ITransactionsReadResponseDto;
    try {
      responseJson = await response.value!.json<ITransactionsReadResponseDto>();
    } catch (exception) {
      console.error("Failed to parse json!", exception);
      return Promise.resolve(new Either(null, new ApiErrorResponse()));
    }

    return Promise.resolve(new Either(responseJson));
  }
}

export default TransactionsApiService;
