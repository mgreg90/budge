import { ISessionCreateResponseDto } from "@/types/apiResponses.types";
import { JwtTokenBody, LOCAL_STORAGE_SESSION_KEY } from "@/types/domain.types";
import jwtDecode from "jwt-decode";

export default class Session {
  userId: string;
  email: string;
  token: string;
  private _parsedToken?: JwtTokenBody;
  private _expiresAt?: number = undefined;
  private _issuer?: string = undefined;

  static ISSUER = "com.budge";

  constructor(
    userId: string,
    email: string,
    token: string,
    parsedToken?: JwtTokenBody
  ) {
    this.userId = userId;
    this.email = email;
    this.token = token;
    this._parsedToken = parsedToken;
  }

  static fromResponseDto(dto: ISessionCreateResponseDto): Session {
    return new Session(dto.user.id, dto.user.email, dto.token);
  }

  static fromToken(token?: string | null): Session | null {
    if (!token) token = Session.readToken();
    if (!token) return null;

    const parsedToken = jwtDecode(token) as JwtTokenBody;
    return new Session(
      parsedToken.userId,
      parsedToken.email,
      token,
      parsedToken
    );
  }

  private static readToken(): string | null {
    const jwt = localStorage.getItem(LOCAL_STORAGE_SESSION_KEY);
    if (!jwt) console.log("No token found in localStorage");
    return jwt;
  }

  parsedToken(): JwtTokenBody {
    if (!this._parsedToken)
      this._parsedToken = jwtDecode(this.token) as JwtTokenBody;

    return this._parsedToken;
  }

  expiresAt(): number {
    if (!this._expiresAt) this._expiresAt = this.parsedToken().exp!;
    return this._expiresAt;
  }

  issuer(): string {
    if (!this._issuer) this._issuer = this.parsedToken().iss!;
    return this._issuer;
  }

  isValid(): boolean {
    const now = Date.now() / 1000;

    return this.issuer() == Session.ISSUER && this.expiresAt() > now;
  }
}
