import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { RouterLink } from '@angular/router';
import { Horario } from '../../../core/models/horario.model';
import { AgendamentoService } from '../../../core/services/agendamento.service';
import { HorarioService } from '../../../core/services/horario.service';
import { PrototipoConfigService } from '../../../core/config/prototipo.config';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';
import { listaOuVazia } from '../../../shared/utils/lista';

interface DiaDisponivel {
  data: string;
  horarios: Horario[];
}

@Component({
  selector: 'app-agendamento',
  imports: [RouterLink, MatCardModule, MatButtonModule, MatIconModule, MatProgressBarModule],
  templateUrl: './agendamento.html',
  styleUrl: './agendamento.css',
})
export class Agendamento implements OnInit {
  private readonly horarioService = inject(HorarioService);
  private readonly agendamentoService = inject(AgendamentoService);
  private readonly configuracao = inject(PrototipoConfigService);
  private readonly notificacao = inject(NotificacaoService);

  protected readonly carregando = signal(true);
  protected readonly enviando = signal(false);
  protected readonly horarios = signal<Horario[]>([]);
  protected readonly horarioSelecionado = signal<Horario | null>(null);
  protected readonly barbeiroId = this.configuracao.barbeiroId;
  protected readonly servicoId = this.configuracao.servicoId;

  protected readonly dias = computed<DiaDisponivel[]>(() => {
    const grupos = new Map<string, Horario[]>();

    for (const horario of this.horarios()) {
      const lista = grupos.get(horario.dataHorario) ?? [];
      lista.push(horario);
      grupos.set(horario.dataHorario, lista);
    }

    return Array.from(grupos.entries())
      .sort(([dataA], [dataB]) => dataA.localeCompare(dataB))
      .map(([data, horarios]) => ({
        data,
        horarios: [...horarios].sort((a, b) => a.hora.localeCompare(b.hora)),
      }));
  });

  ngOnInit(): void {
    this.carregar();
  }

  protected selecionar(horario: Horario): void {
    this.horarioSelecionado.set(horario);
  }

  protected confirmar(): void {
    const horario = this.horarioSelecionado();
    if (!horario) {
      this.notificacao.erro('Selecione um horário disponível.');
      return;
    }

    this.enviando.set(true);
    this.agendamentoService.agendar(horario.id, { idServico: this.servicoId() }).subscribe({
      next: () => {
        this.enviando.set(false);
        this.notificacao.sucesso('Agendamento realizado com sucesso!');
        this.carregar();
      },
      error: (erro: unknown) => {
        this.enviando.set(false);
        this.notificacao.erro(extrairMensagemErro(erro));
      },
    });
  }

  protected formatarData(data: string): string {
    const [ano, mes, dia] = data.split('-');
    return `${dia}/${mes}/${ano}`;
  }

  protected formatarHora(hora: string): string {
    return hora.slice(0, 5);
  }

  private carregar(): void {
    this.carregando.set(true);
    this.horarioSelecionado.set(null);

    this.horarioService
      .listarDisponiveis(this.barbeiroId())
      .pipe(listaOuVazia())
      .subscribe({
        next: (horarios) => {
          this.horarios.set(horarios);
          this.carregando.set(false);
        },
        error: (erro: unknown) => {
          this.carregando.set(false);
          this.notificacao.erro(extrairMensagemErro(erro));
        },
      });
  }
}
