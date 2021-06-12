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
  id: string;
  token: string;
}
