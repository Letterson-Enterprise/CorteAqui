import { Papel } from './papel.model';

export interface LoginRequest {
  nomeUsuario: string;
  senha: string;
}

export interface TokenResposta {
  token: string;
}

export interface CadastroClienteRequest {
  nome: string;
  sobreNome: string;
  nomeUsuario: string;
  email: string;
  senha: string;
}

export interface CadastroBarbeiroRequest {
  nome: string;
  sobreNome: string;
  cpf: string;
  nomeUsuario: string;
  email: string;
  senha: string;
}

export interface Sessao {
  token: string;
  papel: Papel;
  expiracao: number;
}
