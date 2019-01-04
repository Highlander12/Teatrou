import { UsuarioService } from './../../services/usuario.service';
import { Usuario, UsuarioDTO } from './../../model/usuario.model';
import { ErroHandlerService } from './../../services/errohandler.service';
import { OAuthService } from './../../services/oauth.service';
import { Component, OnInit } from "@angular/core";
import { Router } from '@angular/router';

@Component({
  selector: "login",
  templateUrl: "login.component.html"
})
export class LoginComponent  {

  public usuario: UsuarioDTO;
  public loading: boolean;
  public createAccount = false;

  constructor(
    private authService: OAuthService,
    private erroHandlerService: ErroHandlerService,
    private usuarioService: UsuarioService,
    private router: Router
  ) {
    this.usuario = new UsuarioDTO();
  }

  login(usuario: string, senha: string) {
    this.loading = true;
    this.authService
      .login(this.usuario.email, this.usuario.senha)
      .then( () => {
        this.loading = false;
        this.router.navigate(["/eventos"]);
      })
      .catch(erro => {
        this.loading = false;
        this.erroHandlerService.handle(erro);
      });
  }

  criar() {
    this.loading = true;
    this.usuarioService
       .criar(this.usuario)
       .then(() => {
         this.loading = false;
         localStorage.setItem("Produtor", "true");
         this.login(this.usuario.email, this.usuario.senha);
        })
        .catch(erro => {
         this.loading = false;
         this.erroHandlerService.handle(erro)}
        );
  }
}
