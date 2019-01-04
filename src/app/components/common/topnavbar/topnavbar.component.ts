import { ErroHandlerService } from './../../../services/errohandler.service';
import { Router } from "@angular/router";
import { LogoutService } from "./../../../services/logout.service";
import { OAuthService } from "./../../../services/oauth.service";
import { Component } from "@angular/core";
import { smoothlyMenu } from "../../../app.helpers";
declare var jQuery: any;

@Component({
  selector: "topnavbar",
  templateUrl: "topnavbar.component.html",
  styleUrls: ["topnavbar.component.scss"]
})
export class TopNavbarComponent {
  constructor(
    private auth: OAuthService,
    private logoutService: LogoutService,
    private errorHandler: ErroHandlerService,
    private router: Router
  ) {}

  toggleNavigation(): void {
    jQuery("body").toggleClass("mini-navbar");
    smoothlyMenu();
  }

  logout() {
    this.logoutService
      .logout()
      .then(() => {
        localStorage.removeItem("Produtor");
        this.router.navigate(["/login"]);
      })
      .catch(erro => this.errorHandler.handle(erro));
  }
}
