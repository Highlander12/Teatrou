import { environment } from "./../../environments/environment";
import { OAuthService } from "./oauth.service";
import { AuthHttp } from "angular2-jwt";
import { Injectable } from "@angular/core";

@Injectable()
export class LogoutService {
  tokensRenokeUrl: string;

  constructor(private http: AuthHttp, private auth: OAuthService) {
    this.tokensRenokeUrl = `${environment.apiUrl}api/tokens/revoke`;
  }

  logout() {
    return this.http
      .delete(this.tokensRenokeUrl, { withCredentials: true })
      .toPromise()
      .then(() => {
        this.auth.limparAccessToken();
      });
  }
}
