import { Evento } from './evento.model';
import { Compra } from './compra.model';
export class Ingresso {
  constructor(
    public codigo?: number,
    public evento = new Evento(),
    public compra = new Compra(),
    public faixaEtaria?: string,
    public status?: string,
    public ativo?: boolean
  ) {}
}
