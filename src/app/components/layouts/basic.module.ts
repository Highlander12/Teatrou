import { PaymentCompletedComponent } from './../payment-completed/payment-completed.component';
import { ErroHandlerService } from './../../services/errohandler.service';
import { JwtHelper } from 'angular2-jwt';
import { PagSeguroService } from './../../services/pagseguro.service';
import { OAuthService } from './../../services/oauth.service';
import { UsuarioService } from './../../services/usuario.service';
import { CompraService } from './../../services/compra.service';
import { CurrencyMaskModule } from "ng2-currency-mask";
import { EventoService } from './../../services/evento.service';
import { ShoppingCartComponent } from "./../shopping-cart/shoppingcart.component";
import { LoginComponent } from "./../login/login.component";
import { EventComponent } from "./../event/event.component";
import { BreadCrumbComponent } from "./../common/breadcrumb/breadcrumb.component";
import { DetailEventComponent } from "./../detail-event/detail-event.component";
import { CardComponent } from "./../common/card/card.component";
import { LOCALE_ID, NgModule } from "@angular/core";
import { FooterComponent } from "../common/footer/footer.component";
import { NavigationComponent } from "../common/navigation/navigation.component";
import { TopNavbarComponent } from "../common/topnavbar/topnavbar.component";
import { HomeComponent } from "../home/home.component";
import { BrowserModule } from "@angular/platform-browser";
import { RouterModule } from "@angular/router";
import { BsDropdownModule, ModalModule } from "ngx-bootstrap";
import { PrimeModule } from "../common/prime/prime.module";
import { BasicComponent } from "./basic.component";
import { PurchaseStepsComponent } from "../purchase-steps/purchasesteps.component";
import { LogoutService } from '../../services/logout.service';

@NgModule({
  declarations: [
    BreadCrumbComponent,
    FooterComponent,
    BasicComponent,
    NavigationComponent,
    TopNavbarComponent,
    HomeComponent,
    CardComponent,
    DetailEventComponent,
    EventComponent,
    PurchaseStepsComponent,
    LoginComponent,
    ShoppingCartComponent,
    PaymentCompletedComponent
  ],
  imports: [
    BrowserModule,
    CurrencyMaskModule,
    PrimeModule,
    RouterModule,
    BsDropdownModule.forRoot(),
    ModalModule.forRoot()
  ],
  exports: [
    BreadCrumbComponent,
    FooterComponent,
    BasicComponent,
    ModalModule,
    NavigationComponent,
    TopNavbarComponent,
    HomeComponent,
    CardComponent,
    DetailEventComponent,
    EventComponent,
    PurchaseStepsComponent,
    LoginComponent,
    ShoppingCartComponent,
    PaymentCompletedComponent
  ],
  providers: [
    EventoService,
    CompraService,
    UsuarioService,
    PagSeguroService,
    LogoutService,
    ErroHandlerService,
    OAuthService,
    JwtHelper
  ]
})
export class BasicModule {}
