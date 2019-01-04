import { Usuario, UsuarioDTO } from './../model/usuario.model';
import { Injectable } from "@angular/core";
import { AuthHttp } from "angular2-jwt";
import { environment } from '../../environments/environment';

@Injectable()
export class UsuarioService {
  private usuarioUrl: string;

  constructor(private auth: AuthHttp) {
    this.usuarioUrl = `${environment.apiUrl}api/usuario`;
  }

  criar(usuario: UsuarioDTO): Promise<any> {
    return this.auth
    .post(`${this.usuarioUrl}`, JSON.stringify(usuario))
    .toPromise()
    .then(response => response.json());
  }

  alterar(codigo: Number, usuario: Usuario): Promise<Usuario> {
    return this.auth
    .put(`${this.usuarioUrl}/${codigo}`, JSON.stringify(usuario))
    .toPromise()
    .then(response => response.json());
  }
}
