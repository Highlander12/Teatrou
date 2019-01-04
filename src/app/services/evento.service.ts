import { OAuthService } from './oauth.service';
import { EventoFilter, Evento } from "./../model/evento.model";
import { environment } from "./../../environments/environment";
import { AuthHttp } from "angular2-jwt";

import { Headers, URLSearchParams } from "@angular/http";

import { Injectable } from "@angular/core";
import "rxjs/add/operator/toPromise";
import * as moment from "moment";

@Injectable()
export class EventoService {
  private eventoUrl: string;

  constructor(private auth: AuthHttp, private authService: OAuthService) {
    this.eventoUrl = `${environment.apiUrl}api/evento`;
  }

  filtrar(filtro: EventoFilter): Promise<any> {
    const params = new URLSearchParams();
    params.set("page", filtro.pagina.toString());
    params.set("size", filtro.itensPorPagina.toString());
    params.set("ativo", "true")

    if (filtro.dataEventoDe) {
      params.set(
        "dataEventoAte",
        moment(filtro.dataEventoDe).format("YYYY-MM-DD")
      );
    }
    if (filtro.dataEventoAte) {
      params.set(
        "dataEventoAte",
        moment(filtro.dataEventoAte).format("YYYY-MM-DD")
      );
    }
    if (filtro.tema) {
      params.set("tema", filtro.tema);
    }
    if (filtro.titulo) {
      params.set("titulo", filtro.titulo);
    }
    if (this.authService.jwtPayload.produtor) {
      params.set("codigoUsuario", this.authService.jwtPayload.codigo);
    }

    return this.auth
      .get(`${this.eventoUrl}`, { search: params})
      .toPromise()
      .then(response => {
        const responseJson = response.json();
        const eventos = responseJson.content;
        const total = responseJson.totalElements;
        const resultado = {
          eventos,
          total
        };
        return resultado;
      });
  }

  excluir(codigo: number): Promise<void> {
    /* const headers = new Headers();
    headers.append(
      "Authorization",
      "Basic YWRtaW5AYWxnYW1vbmV5LmNvbTphZG1pbg=="
    );*/

    return this.auth
      .delete(`${this.eventoUrl}/${codigo}`)
      .toPromise()
      .then(() => null);
  }

  criar(evento: Evento): Promise<Evento> {
    /*const headers = new Headers();
    headers.append(
      "Authorization",
      "Basic YWRtaW5AYWxnYW1vbmV5LmNvbTphZG1pbg=="
    );
    headers.append("Content-Type", "application/json");*/

    return this.auth
      .post(`${this.eventoUrl}`, JSON.stringify(evento))
      .toPromise()
      .then(response => response.json());
  }

  buscarPorPk(codigo: number): Promise<Evento> {
    /*const headers = new Headers();
    headers.append(
      "Authorization",
      "Basic YWRtaW5AYWxnYW1vbmV5LmNvbTphZG1pbg=="
    );*/

    return this.auth
      .get(`${this.eventoUrl}/${codigo}`)
      .toPromise()
      .then(response => {
        const evento = response.json() as Evento;
        this.converterStringsParaDatas([evento]);
        return evento;
      });
  }




  atualizar(evento: Evento): Promise<Evento> {
    /*const headers = new Headers();
    headers.append(
      "Authorization",
      "Basic YWRtaW5AYWxnYW1vbmV5LmNvbTphZG1pbg=="
    );
    headers.append("Content-Type", "application/json");*/

    return this.auth
      .put(
        `${this.eventoUrl}/${evento.codigo}`,
        JSON.stringify(evento)
      )
      .toPromise()
      .then(response => response.json());
  }

  urlUploadAnexo(): string {
    return `${this.eventoUrl}/image`;
  }

  private converterStringsParaDatas(eventos: Evento[]) {
    for (const evento of eventos) {
      evento.dataEvento = moment(
        evento.dataEvento,
        "YYYY-MM-DD"
      ).toDate();
    }
  }
}
