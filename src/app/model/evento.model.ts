import { Usuario } from "./usuario.model";

export class Evento {
  constructor(
    public codigo?: number,
    public usuario = new Usuario(),
    public anexo?: string,
    public urlAnexo?: string,
    public titulo?: string,
    public descricao?: string,
    public dataEvento?: Date,
    public horaInicial?: string,
    public horaFinal?: string,
    public tema?: string,
    public endereco?: string,
    public quantidadeIngresso = 0,
    public valorIngresso?: number,
    public ativo?: boolean
  ) {}
}


export class EventoFilter {
  constructor(
    public tema?: string,
    public titulo?: string,
    public dataEventoDe?: Date,
    public dataEventoAte?: Date,
    public ativo?: boolean,
    public pagina = 0,
    public itensPorPagina = 5
  ) {}
}
