import { environment } from "./../../environments/environment";
import { JwtHelper } from "angular2-jwt";
import { Headers, Http } from "@angular/http";
import { Injectable, OnInit } from "@angular/core";

@Injectable()
export class OAuthService {
  oauthTokenUrl: string;
  jwtPayload: any;

  constructor(private http: Http, private jwtHelper: JwtHelper) {
    this.carregarToken();
    this.oauthTokenUrl = `${environment.apiUrl}oauth/token`;
  }

  login(usuario: string, senha: string): Promise<void> {
    const headers = new Headers();
    headers.append("Content-Type", "application/x-www-form-urlencoded");
    headers.append("Authorization", "Basic YW5ndWxhcjpAbmd1bEBhMA==");
    const body = `username=${usuario}&password=${senha}&grant_type=password`;

    return this.http
      .post(this.oauthTokenUrl, body, { headers, withCredentials: true })
      .toPromise()
      .then(response => {
        this.armazenarToken(response.json().access_token);
      })
      .catch(response => {
        if (response.status === 400) {
          const responseJson = response.json();
          if (responseJson.error === "invalid_grant") {
            return Promise.reject("Usuario ou senha invalidos!");
          }
        }
        return Promise.reject(response);
      });
  }

  temPermissao(permissao: string) {
    return this.jwtPayload && this.jwtPayload.authorities.includes(permissao);
  }

  armazenarToken(token: string) {
    this.jwtPayload = this.jwtHelper.decodeToken(token);
    localStorage.setItem("token", token);
  }

  limparAccessToken() {
    localStorage.removeItem("token");
    this.jwtPayload = null;
  }

  carregarToken() {
    const token = localStorage.getItem("token");
    if (token) {
      this.armazenarToken(token);
    }
  }

  temQualquerPermissao(roles) {
    for (const role of roles) {
      if (this.temPermissao(role)) {
        return true;
      }
    }
    return false;
  }

  isAccessTokenInvalido() {
    const token = localStorage.getItem("token");

    return !token || this.jwtHelper.isTokenExpired(token);
  }

  obterNovoAccessToken(): Promise<void> {
    const headers = new Headers();
    headers.append("Content-Type", "application/x-www-form-urlencoded");
    headers.append("Authorization", "Basic YW5ndWxhcjpAbmd1bEBhMA==");
    const body = `grant_type=refresh_token`;
    return this.http
      .post(this.oauthTokenUrl, body, { headers, withCredentials: true })
      .toPromise()
      .then(response => {
        this.armazenarToken(response.json().access_token);
        console.log("Novo access token criado!");
        return Promise.resolve(null);
      })
      .catch(response => {
        console.error("erro ao renovar token.", response);
        return Promise.resolve(null);
      });
  }
}
