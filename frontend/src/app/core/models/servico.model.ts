export interface Servico {
  id: number;
  nomeServico: string;
  preco: number;
}

export interface ServicoRequest {
  nomeServico: string;
  preco: number;
  idCategoria: number;
}
