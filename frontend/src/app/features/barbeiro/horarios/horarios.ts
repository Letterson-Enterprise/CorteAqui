import { Component, OnInit, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule } from '@angular/material/table';
import { Horario } from '../../../core/models/horario.model';
import { HorarioService } from '../../../core/services/horario.service';
import {
  DadosConfirmacao,
  DialogoConfirmacao,
} from '../../../shared/components/dialogo-confirmacao/dialogo-confirmacao';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';
import { listaOuVazia } from '../../../shared/utils/lista';
import { DadosHorarioDialogo, DialogoHorario } from './dialogo-horario';

@Component({
  selector: 'app-horarios',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatProgressBarModule,
  ],
  templateUrl: './horarios.html',
  styleUrl: '../crud.css',
})
export class Horarios implements OnInit {
  private readonly horarioService = inject(HorarioService);
  private readonly dialog = inject(MatDialog);
  private readonly notificacao = inject(NotificacaoService);

  protected readonly carregando = signal(true);
  protected readonly horarios = signal<Horario[]>([]);
  protected readonly colunas = ['data', 'hora', 'status', 'acoes'];

  ngOnInit(): void {
    this.carregar();
  }

  protected abrirNova(): void {
    this.abrirDialogo();
  }

  protected editar(horario: Horario): void {
    this.abrirDialogo(horario);
  }

  protected excluir(horario: Horario): void {
    const referencia = this.dialog.open<DialogoConfirmacao, DadosConfirmacao, boolean>(
      DialogoConfirmacao,
      {
        width: '400px',
        data: {
          titulo: 'Excluir horário',
          mensagem: `Deseja realmente excluir o horário de ${this.formatarData(
            horario.dataHorario,
          )} às ${this.formatarHora(horario.hora)}?`,
        },
      },
    );

    referencia.afterClosed().subscribe((confirmado) => {
      if (!confirmado) {
        return;
      }

      this.horarioService.excluir(horario.id).subscribe({
        next: () => {
          this.notificacao.sucesso('Horário excluído com sucesso.');
          this.carregar();
        },
        error: (erro: unknown) => this.notificacao.erro(extrairMensagemErro(erro)),
      });
    });
  }

  protected formatarData(data: string): string {
    const [ano, mes, dia] = data.split('-');
    return `${dia}/${mes}/${ano}`;
  }

  protected formatarHora(hora: string): string {
    return hora.slice(0, 5);
  }

  private abrirDialogo(horario?: Horario): void {
    const referencia = this.dialog.open<DialogoHorario, DadosHorarioDialogo, boolean>(
      DialogoHorario,
      {
        width: '420px',
        data: { horario },
      },
    );

    referencia.afterClosed().subscribe((salvo) => {
      if (salvo) {
        this.notificacao.sucesso('Horário salvo com sucesso.');
        this.carregar();
      }
    });
  }

  private carregar(): void {
    this.carregando.set(true);
    this.horarioService
      .listarPorBarbeiro()
      .pipe(listaOuVazia())
      .subscribe({
        next: (horarios) => {
          this.horarios.set(this.ordenar(horarios));
          this.carregando.set(false);
        },
        error: (erro: unknown) => {
          this.carregando.set(false);
          this.notificacao.erro(extrairMensagemErro(erro));
        },
      });
  }

  private ordenar(horarios: Horario[]): Horario[] {
    return [...horarios].sort((a, b) =>
      `${a.dataHorario}T${a.hora}`.localeCompare(`${b.dataHorario}T${b.hora}`),
    );
  }
}
