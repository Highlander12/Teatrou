import { Permissao } from "./permissao.model";

export class Usuario {
  constructor(
      public codigo?: number,
      public nome?: string,
      public email?: string,
      public senha?: string,
      public permissoes = new Array<Permissao>()
  ) {}
}


export class UsuarioDTO extends Usuario {
  constructor(
    public confirmacaoSenha?: string,
    public productor= false
  ) {
    super();
  }
}
