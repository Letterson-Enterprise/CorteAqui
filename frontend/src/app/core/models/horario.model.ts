export type StatusHorario = 'DISPONIVEL' | 'OCUPADO' | 'REMOVIDO';

export interface Horario {
  id: number;
  dataHorario: string;
  hora: string;
  statusHorario: StatusHorario;
  barbeiroId: number;
}

export interface HorarioRequest {
  dataHorario: string;
  hora: string;
}
