import { Component, OnInit, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { forkJoin } from 'rxjs';
import { Horario } from '../../../core/models/horario.model';
import { CategoriaService } from '../../../core/services/categoria.service';
import { HorarioService } from '../../../core/services/horario.service';
import { ServicoService } from '../../../core/services/servico.service';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';
import { listaOuVazia } from '../../../shared/utils/lista';

@Component({
  selector: 'app-painel-barbeiro',
  imports: [DatePipe, MatCardModule, MatIconModule, MatProgressBarModule],
  templateUrl: './painel-barbeiro.html',
  styleUrl: './painel-barbeiro.css',
})
export class PainelBarbeiro implements OnInit {
  private readonly categoriaService = inject(CategoriaService);
  private readonly servicoService = inject(ServicoService);
  private readonly horarioService = inject(HorarioService);
  private readonly notificacao = inject(NotificacaoService);

  protected readonly carregando = signal(true);
  protected readonly totalCategorias = signal(0);
  protected readonly totalServicos = signal(0);
  protected readonly proximosHorarios = signal<Horario[]>([]);

  ngOnInit(): void {
    this.carregarDados();
  }

  private carregarDados(): void {
    this.carregando.set(true);

    forkJoin({
      categorias: this.categoriaService.listar().pipe(listaOuVazia()),
      servicos: this.servicoService.listar().pipe(listaOuVazia()),
      horarios: this.horarioService.listarPorBarbeiro().pipe(listaOuVazia()),
    }).subscribe({
      next: ({ categorias, servicos, horarios }) => {
        this.totalCategorias.set(categorias.length);
        this.totalServicos.set(servicos.length);
        this.proximosHorarios.set(this.ordenarHorarios(horarios).slice(0, 5));
        this.carregando.set(false);
      },
      error: (erro: unknown) => {
        this.carregando.set(false);
        this.notificacao.erro(extrairMensagemErro(erro));
      },
    });
  }

  private ordenarHorarios(horarios: Horario[]): Horario[] {
    return [...horarios].sort((a, b) =>
      `${a.dataHorario}T${a.hora}`.localeCompare(`${b.dataHorario}T${b.hora}`),
    );
  }
}
