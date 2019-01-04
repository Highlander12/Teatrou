import { EventoFilter } from './../../model/evento.model';
import { EventoService } from './../../services/evento.service';
import { OAuthService } from './../../services/oauth.service';
import { Component, OnDestroy, OnInit } from "@angular/core";
import { Evento } from '../../model/evento.model';
import { Title } from '@angular/platform-browser';
import { LazyLoadEvent } from 'primeng/primeng';
import { Locale } from '../../model/locale.model';
import { ErroHandlerService } from '../../services/errohandler.service';

@Component({
  selector: "home",
  templateUrl: "home.component.html",
  styleUrls: ["home.component.scss"]
})
export class HomeComponent implements OnInit {
  public filters: boolean;
  public locale = Locale.BR;
  public eventos = [];
  public filtro = new EventoFilter();
  public totalRegistros = 0;
  public loading;


  constructor(
    private auth: OAuthService,
    private eventoService: EventoService,
    private erroHandleService: ErroHandlerService,
    private title: Title,
    ) {}

  ngOnInit() {
    this.title.setTitle("Eventos");
    this.pesquisar();
  }


  aoMudarPagina(event: LazyLoadEvent) {
    const pagina = event.first / event.rows;
    const rows = event.rows;
    this.pesquisar(pagina, rows);
  }

  pesquisar(pagina = 0, rows = 6) {
    this.loading = true;
    this.filtro.pagina = pagina;
    this.filtro.itensPorPagina = rows;
    this.eventoService
       .filtrar(this.filtro)
       .then(resultado => {
          this.loading = false;
          this.eventos = resultado.eventos;
          this.totalRegistros = resultado.total;
       })
       .catch(erro =>  {
         this.loading = false;
         this.erroHandleService.handle(erro)
        });
  }

  enabledFilter(event) {
       this.filters = !this.filters;
  }
}
