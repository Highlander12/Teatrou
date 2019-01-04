import { CompraDTO, Compra } from "./../../model/compra.model";
import { PagSeguroService } from "./../../services/pagseguro.service";
import { ErroHandlerService } from "./../../services/errohandler.service";
import { ActivatedRoute } from "@angular/router";
import { EventoService } from "./../../services/evento.service";
import { OAuthService } from "./../../services/oauth.service";
import { Component, OnInit } from "@angular/core";
import { smoothlyMenu } from "../../app.helpers";
import { Title } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Evento } from "../../model/evento.model";
import { Location } from "@angular/common";
declare var jQuery: any;

@Component({
  selector: "purchase-steps",
  templateUrl: "purchasesteps.component.html",
  styleUrls: ["purchasesteps.component.scss"]
})
export class PurchaseStepsComponent implements OnInit {
  public evento: Evento;
  public compraDTO: CompraDTO;
  public linkPagamento: string;

  constructor(
    private auth: OAuthService,
    private title: Title,
    private eventoService: EventoService,
    private pagSeguroService: PagSeguroService,
    private router: Router,
    private route: ActivatedRoute,
    private erroHandler: ErroHandlerService,
    private location: Location
  ) {
    this.evento = new Evento();
    this.compraDTO = new CompraDTO();
  }

  ngOnInit() {
    this.title.setTitle("Bora comprar");
    const codigoEvento = this.route.snapshot.params["id"];
    this.buscarPorPk(codigoEvento);
  }

  criarPagamento(event) {
    this.pagSeguroService
      .criarPagamento(this.compraDTO)
      .then(response => {
        this.linkPagamento = response._body;
        window.location.href = this.linkPagamento;
        // this.router.navigate([`https://${this.linkPagamento}`]);
      })
      .catch(erro => this.erroHandler.handle(erro));
  }

  buscarPorPk(codigo) {
    this.eventoService
      .buscarPorPk(codigo)
      .then(response => {
        this.evento = response;
        this.compraDTO.codigoEvento = this.evento.codigo;
        this.compraDTO.codigoUsuario = this.auth.jwtPayload.codigo;
        console.log(this.evento);
      })
      .catch(erro => this.erroHandler.handle(erro));
  }

  incrementar(meia: boolean) {
    if (!meia) {
      this.compraDTO.ingressosInteira = this.compraDTO.ingressosInteira + 1;
    } else {
      this.compraDTO.ingressosMeia = this.compraDTO.ingressosMeia + 1;
    }
  }

  decrementar(meia: boolean) {
    if (!meia) {
      this.compraDTO.ingressosInteira = this.compraDTO.ingressosInteira - 1;
    } else {
      this.compraDTO.ingressosMeia = this.compraDTO.ingressosMeia - 1;
    }
  }

  getValorTotal() {
    return (
      this.evento.valorIngresso * this.compraDTO.ingressosInteira +
      (this.evento.valorIngresso / 2) * this.compraDTO.ingressosMeia
    );
  }

  voltar() {
    if (window.history.length > 1) this.location.back();
    else this.router.navigate(["/eventos"]);
  }
}
