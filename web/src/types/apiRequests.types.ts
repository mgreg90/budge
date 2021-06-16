export interface IUserCreateRequestDto {
  email: string;
  password: string;
  passwordConfirmation: string;
}

export interface ISessionCreateRequestDto {
  email: string;
  password: string;
}

export interface IRequestOptions {
  body?: any;
  skipAuth?: boolean;
}
