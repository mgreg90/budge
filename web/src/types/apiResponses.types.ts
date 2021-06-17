export interface IApiErrorResponse {
  results?: IApiErrorResponseResults[];
  message: string;
  type: string;
}

export interface IApiErrorResponseResults {
  field: string;
  message: string;
}

export interface IUserCreateResponseDto {
  token: string;
  user: {
    id: string;
    email: string;
  };
}

export interface ISessionCreateResponseDto {
  token: string;
  user: {
    id: string;
    email: string;
  };
}

export interface ISessionReadResponseDto {
  isTokenValid: boolean;
}

export interface ITransactionsReadResponseDto {
  message: string;
}
