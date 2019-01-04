import { Injectable } from "@angular/core";
import { AuthHttp } from "angular2-jwt";
import { OAuthService } from "./oauth.service";
import { environment } from "../../environments/environment";
import { CompraDTO } from "../model/compra.model";







@Injectable()
export class PagSeguroService {
  private pagSeguroUrl: string;

  constructor(private auth: AuthHttp, private authService: OAuthService) {
    this.pagSeguroUrl = `${environment.apiUrl}api/pag-seguro`;
  }


  criarPagamento(compra: CompraDTO) : Promise<any> {
    return this.auth
    .post(`${this.pagSeguroUrl}/pagamento`, JSON.stringify(compra))
    .toPromise()
    .then(response => {
         return response;
    });
  }

}
