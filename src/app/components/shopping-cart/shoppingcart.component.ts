import { CompraService } from './../../services/compra.service';
import { CompraFilter } from './../../model/compra.model';
import { OnInit, Component } from "@angular/core";
import { OAuthService } from '../../services/oauth.service';
import { EventoService } from '../../services/evento.service';
import { ErroHandlerService } from '../../services/errohandler.service';
import { Title } from '@angular/platform-browser';
import { LazyLoadEvent, SelectItem } from 'primeng/primeng';
import { Situacao } from '../../model/situacao.enum';

import { Locale } from '../../model/locale.model';
import { Usuario } from '../../model/usuario.model';





@Component({
  selector: "shoppingcart",
  templateUrl: "shoppingcart.component.html",
  styleUrls: ["shoppingcart.component.scss"]
})
export class ShoppingCartComponent implements OnInit {
  public situacao: SelectItem[];
  public locale = Locale.BR;
  public compras = [];
  public filtro = new CompraFilter();
  public totalRegistros = 0;
  public loading;

  constructor(
    private auth: OAuthService,
    private compraService: CompraService,
    private erroHandleService: ErroHandlerService,
    private title: Title,
    ) {
      this.situacao = Situacao.optionsSituacao;
    }


  ngOnInit() {
    this.title.setTitle("Minhas compras");
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
    this.filtro.codigoUsuario = this.auth.jwtPayload.codigo;
    this.compraService
       .buscarCompras(this.filtro)
       .then(resultado => {
          this.loading = false;
          this.compras = resultado.compras;
          this.totalRegistros = resultado.total;
       })
       .catch(erro =>  {
         this.loading = false;
         this.erroHandleService.handle(erro)
        });
  }


}
