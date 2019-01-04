import { CompraFilter } from './../model/compra.model';
import { environment } from "./../../environments/environment";
import { AuthHttp } from "angular2-jwt";

import { Headers, ResponseContentType, URLSearchParams } from '@angular/http';

import { Injectable } from "@angular/core";
import 'rxjs/add/operator/toPromise';
import * as moment from 'moment';
import { Ingresso } from '../model/ingresso.model';

@Injectable()
export class CompraService {
  private compraUrl: string;

  constructor(private auth: AuthHttp) {
    this.compraUrl = `${environment.apiUrl}api/compras`;
  }

  buscarCompras(filtro: CompraFilter): Promise<any>  {
    const params = new URLSearchParams();
    params.set("page", filtro.pagina.toString());
    params.set("size", filtro.itensPorPagina.toString());

    if(filtro.dataCompraDe) {
      params.set("dataCompraDe",
      moment(filtro.dataCompraDe).format("YYYY-MM-DD"))
    }

    if(filtro.dataCompraAte) {
      params.set("dataCompraAte",
      moment(filtro.dataCompraAte).format("YYYY-MM-DD"))
    }
    if(filtro.codigoUsuario) {
      params.set("codigoUsuario", filtro.codigoUsuario.toString());
    }
    if(filtro.situacao) {
      params.set("situacao", filtro.situacao);
    }

    return this.auth
    .get(`${this.compraUrl}`, { search: params})
    .toPromise()
    .then(response => {
      const responseJson = response.json();
      const compras = responseJson.content;
      const total = responseJson.totalElements;
      const resultado = {
        compras,
        total
      };
      return resultado;
    });

  }

  buscarIngressos(codigoEvento: number, codigoCompra: String) : Promise<Ingresso[]> {
    return this.auth
    .get(`${this.compraUrl}/${codigoEvento}/${codigoCompra}`)
    .toPromise()
    .then(response => {
      const ingressos = response.json() as Ingresso[];
      return ingressos;
    });
  }
}
