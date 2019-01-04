import { Router } from '@angular/router';
import { OAuthService } from "./../../../services/oauth.service";
import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";

@Component({
  selector: "breadcrumb",
  templateUrl: "breadcrumb.component.html",
  styleUrls: ["breadcrumb.component.scss"]
})
export class BreadCrumbComponent implements OnInit {
  @Input() label: string;
  @Input() filter: boolean;
  @Input() shopping: boolean;
  @Output() enabledFilter = new EventEmitter();

  public value = false;

  constructor(public auth: OAuthService, private router: Router) {}

  ngOnInit() {}

  filterOn() {
    this.value = !this.value;
    this.enabledFilter.emit(this.value);
  }

  novo() {
       this.router.navigate(["/eventos/novo"])
  }

  getButtonNew() {
    return this.auth.temPermissao('ROLE_CADASTRAR_EVENTO') && this.router.url !== "/eventos/novo";
  }

  getShopping() {
    return this.auth.temPermissao('ROLE_PESQUISAR_COMPRA') && this.router.url !== "/minhas-compras";
  }

  minhasCompras() {
    this.router.navigate(["/minhas-compras"]);
  }
}
