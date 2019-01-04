import { OAuthService } from './../../services/oauth.service';
import { ToastrService } from 'ngx-toastr';
import { FaixaEtaria } from './../../model/faixaetaria.enum';
import { ErroHandlerService } from "./../../services/errohandler.service";
import { Title } from "@angular/platform-browser";
import { EventoService } from "./../../services/evento.service";
import { Component, OnInit, ViewChild } from "@angular/core";
import { Evento } from "../../model/evento.model";
import { SelectItem, FileUpload } from "primeng/primeng";
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from "moment";
import { Locale } from '../../model/locale.model';
import { Usuario } from '../../model/usuario.model';

@Component({
  selector: "event",
  templateUrl: "event.component.html",
  styleUrls: ["event.component.scss"]
})
export class EventComponent implements OnInit {
  @ViewChild("fileUpload") fileUpload: FileUpload;
  public faixaEtaria: SelectItem[];
  public evento: Evento;
  public loading = false;
  public locale = Locale.BR;
  public label = "Novo";


  constructor(
    private eventoService: EventoService,
    private title: Title,
    private errorHandler: ErroHandlerService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router,
    private auth: OAuthService
  ) {
    this.evento = new Evento();
    this.faixaEtaria = FaixaEtaria.optionsFaixaEtaria;
  }

  ngOnInit() {
    this.title.setTitle("Novo Evento");
    const codigoEvento = this.route.snapshot.params["id"];
    if(codigoEvento) {
      this.buscarPorPk(codigoEvento);
      this.atualizarTituloEdicao();
    }
  }

  atualizarTituloEdicao() {
    this.title.setTitle("Edição de Evento");
    this.label = "Editar ";
  }

  buscarPorPk(codigo) {
     this.eventoService
           .buscarPorPk(codigo)
           .then(response => {
             this.evento = response;
           })
           .catch(erro => this.errorHandler.handle(erro));
  }


  antesUploadAnexo(event) {
    event.xhr.setRequestHeader(
      "Authorization",
      "Bearer " + localStorage.getItem("token")
    );
  }

  aoTerminarUploadAnexo(event) {
        const anexo = JSON.parse(event.xhr.response)
       this.evento.anexo = anexo["nome"];
  }

  get urlUploadAnexo() {
    return this.eventoService.urlUploadAnexo();
  }

  diminuir() {
    if(this.evento.quantidadeIngresso > 0)
    this.evento.quantidadeIngresso = this.evento.quantidadeIngresso -1;
  }

  incrementar() {
    console.log(this.evento.quantidadeIngresso)
    this.evento.quantidadeIngresso = this.evento.quantidadeIngresso +1;
  }


  salvar(form: FormControl) {
    if (this.evento.codigo) {
      this.atualizarEvento(form);
    } else {
      this.adicionarEvento(form);
    }
  }

  atualizarEvento(form: FormControl) {
    this.loading = true
    this.formaterDependencies(true);
    this.eventoService
    .atualizar(this.evento)
    .then(response => {
              this.loading = false;
              this.toastr.success("Evento Atualizado com sucesso", "Sucesso!");
              this.evento = response;
            })
            .catch(erro => {
              this.loading = false;
              this.errorHandler.handle(erro)
            });
  }

  adicionarEvento(eventoForm: FormControl) {
    this.loading = true;
    this.fileUpload.upload();
    setTimeout(() => {

      this.formaterDependencies(false)
      this.eventoService
      .criar(this.evento)
      .then(response => {
        this.loading = false;
        this.toastr.success("Evento criado com sucesso.", "Sucesso!");
        this.router.navigate(["/eventos", response.codigo]);
      })
      .catch(erro => {
        this.loading = false;
        this.errorHandler.handle(erro);
      });
    }, 3000);
    }

  voltar() {
    this.router.navigate(["/eventos"]);
  }

  formaterHour(hour) {
    return moment(hour).format("HH:mm") === "Invalid date"
      ? moment(hour, "HH:mm").format("HH:mm")
      : moment(hour).format("HH:mm");
  }

  formaterDependencies(edit: boolean) {
    this.evento.horaInicial = this.formaterHour(this.evento.horaInicial);
    this.evento.horaFinal = this.formaterHour(this.evento.horaFinal);
    this.evento.ativo = true;
    if(!this.evento.usuario) this.evento.usuario = new Usuario();
    this.evento.usuario.codigo = this.auth.jwtPayload.codigo;
  }
}



