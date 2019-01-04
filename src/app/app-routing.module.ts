import { PaymentCompletedComponent } from './components/payment-completed/payment-completed.component';
import { AuthGuard } from "./guards/auth.guard";
import { ShoppingCartComponent } from "./components/shopping-cart/shoppingcart.component";
import { LoginComponent } from "./components/login/login.component";
import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";
import { BasicComponent } from "./components/layouts/basic.component";
import { HomeComponent } from "./components/home/home.component";
import { EventComponent } from "./components/event/event.component";
import { DetailEventComponent } from "./components/detail-event/detail-event.component";
import { PurchaseStepsComponent } from "./components/purchase-steps/purchasesteps.component";

const ROUTES: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  {
    path: "",
    component: BasicComponent,
    children: [
      {
        path: "eventos",
        component: HomeComponent,
        canActivate: [AuthGuard],
        data: { roles: ["ROLE_PESQUISAR_EVENTO"]}
      },
      {
        path: "eventos/novo",
        component: EventComponent,
        canActivate: [AuthGuard],
        data: {roles : ["ROLE_CADASTRAR_EVENTO"]}
      },
      {
        path: "eventos/:id",
        component: EventComponent,
        canActivate: [AuthGuard],
        data: {roles : ["ROLE_CADASTRAR_EVENTO"]}
      },
      {
        path: "eventos/detalhe/:id",
        component: DetailEventComponent,
        canActivate: [AuthGuard]
      },
      {
        path: "eventos/:id/comprar",
        component: PurchaseStepsComponent,
        canActivate: [AuthGuard],
        data: {roles : ["ROLE_REALIZAR_COMPRA"]}
      },
      {
        path: "eventos/:id/comprar/:idCompra/pagamento-finalizado",
        component: PaymentCompletedComponent,
        canActivate: [AuthGuard],
        data: {roles : ["ROLE_REALIZAR_COMPRA"]}
      },
      {
        path: "minhas-compras",
        component: ShoppingCartComponent,
        canActivate: [AuthGuard],
        data: {roles : ["ROLE_PESQUISAR_COMPRA"]}
      }
    ]
  },
  { path: "login", component: LoginComponent },
  { path: "**", redirectTo: "login" }
];

@NgModule({
  imports: [RouterModule.forRoot(ROUTES)],
  exports: [RouterModule]
})
export class AppRountingModule {}
