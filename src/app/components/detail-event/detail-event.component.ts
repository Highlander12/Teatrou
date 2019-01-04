import { ErroHandlerService } from './../../services/errohandler.service';
import { EventoService } from "./../../services/evento.service";
import { OAuthService } from "./../../services/oauth.service";
import { Component, OnInit, ViewChild } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { Title } from '@angular/platform-browser';
import { Evento } from '../../model/evento.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: "detail-event",
  templateUrl: "detail-event.component.html",
  styleUrls: ["detail-event.component.scss"]
})
export class DetailEventComponent implements OnInit {
  @ViewChild('eventModal') eventModal :any;
   public evento: Evento;

  constructor(
    public auth: OAuthService,
    private eventoService: EventoService,
    private router: Router,
    private errorHandler: ErroHandlerService,
    private title: Title,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService
  ) {
    this.evento = new Evento();
  }

  ngOnInit() {
    this.title.setTitle("Detalhamento Evento");
    this.activatedRoute.params.subscribe(params => {
      this.eventoService.buscarPorPk(params["id"])
               .then(resultado => this.evento = resultado)
               .catch(erro => this.errorHandler.handle(erro));
    })
  }


  editar() {
     this.router.navigate(["/eventos", this.evento.codigo]);
  }

  excluir() {
     this.eventoService
        .excluir(this.evento.codigo)
        .then(() => {
          this.toastr.success("Evento excluido com sucesso.", "Sucesso");
        })
        .catch(erro => this.errorHandler.handle(erro))
        this.eventModal.hide();
      }

  confirmacaoExcluir( ) {
    this.eventModal.show(event);
  }

  comprar() {
      this.router.navigate(["/eventos", this.evento.codigo, "comprar"]);
  }

  voltar(){
    this.router.navigate(["/eventos"]);
  }
}
