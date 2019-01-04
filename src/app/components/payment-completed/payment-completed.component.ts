import { Ingresso } from './../../model/ingresso.model';
import { CompraFilter, Compra } from './../../model/compra.model';
import { CompraService } from './../../services/compra.service';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { OnInit, Component } from "@angular/core";
import { ErroHandlerService } from '../../services/errohandler.service';
import { OAuthService } from '../../services/oauth.service';




@Component({
  selector: "payment-completed",
  templateUrl: "payment-completed.component.html",
  styleUrls: ["payment-completed.component.scss"]
})
export class PaymentCompletedComponent implements OnInit {

  public idCompra: string;
  public idEvento: number;
  public compra: Compra;
  public ingressos: Ingresso[];
  public loading = false;

  constructor(
    private auth: OAuthService,
    private route: ActivatedRoute,
    private title: Title,
    private compraService: CompraService,
    private erroHandler: ErroHandlerService
  ) {
    this.compra = new Compra();
    this.ingressos = new Array<Ingresso>();
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.idCompra = params["idCompra"];
      this.idEvento = params["id"];
      this.pesquisarIngressos();

    })
  }

  pesquisarIngressos() {
    this.loading = true;
    this.compraService
      .buscarIngressos(this.idEvento, this.idCompra)
      .then(resultado => {
        this.loading = false;
        this.ingressos = resultado;
        console.log(this.ingressos)
      })
      .catch(erro => {
        this.loading = false;
        this.erroHandler.handle(erro)
      }
      );
  }

  imprimir() {
    window.print();
  }


}
