export interface AgendamentoRequest {
  idServico: number;
}

export type StatusAgendamento = 'AGENDADO' | 'DISPONIVEL' | 'CANCELADO' | 'CONCLUIDO';
