import { Usuario } from "./usuario.model";

export class Compra {
  constructor(
      public codigo?: number,
      public usuario = new Usuario(),
      public quantidadeIngresso?: number,
      public dataCompra?: Date,
      public valorTotal?: number,
      public situacao?: string,
  ) {}
}


export class CompraFilter {
  constructor(
    public codigo?: number,
    public codigoUsuario?: number,
    public dataCompraDe?: Date,
    public dataCompraAte?: Date,
    public situacao?: string,
    public pagina = 0,
    public itensPorPagina = 5
) {}
}

export class CompraDTO {
    constructor(
     public codigoEvento?: number,
     public ingressosMeia = 0,
     public ingressosInteira = 0,
     public codigoUsuario?: number,
    ) {}
}
