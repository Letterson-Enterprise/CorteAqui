import { Servico } from './servico.model';

export interface Categoria {
  id: number;
  nome: string;
  descricao: string;
  servicos: Servico[];
}

export interface CategoriaRequest {
  nome: string;
  descricao: string;
}
