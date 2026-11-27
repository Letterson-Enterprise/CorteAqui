import { Component, OnInit, inject, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule } from '@angular/material/table';
import { forkJoin } from 'rxjs';
import { Categoria } from '../../../core/models/categoria.model';
import { Servico } from '../../../core/models/servico.model';
import { CategoriaService } from '../../../core/services/categoria.service';
import { ServicoService } from '../../../core/services/servico.service';
import {
  DadosConfirmacao,
  DialogoConfirmacao,
} from '../../../shared/components/dialogo-confirmacao/dialogo-confirmacao';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';
import { listaOuVazia } from '../../../shared/utils/lista';
import { DadosServicoDialogo, DialogoServico } from './dialogo-servico';

@Component({
  selector: 'app-servicos',
  imports: [
    CurrencyPipe,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatProgressBarModule,
  ],
  templateUrl: './servicos.html',
  styleUrl: '../crud.css',
})
export class Servicos implements OnInit {
  private readonly servicoService = inject(ServicoService);
  private readonly categoriaService = inject(CategoriaService);
  private readonly dialog = inject(MatDialog);
  private readonly notificacao = inject(NotificacaoService);

  protected readonly carregando = signal(true);
  protected readonly servicos = signal<Servico[]>([]);
  protected readonly categorias = signal<Categoria[]>([]);
  protected readonly colunas = ['nomeServico', 'preco', 'acoes'];

  ngOnInit(): void {
    this.carregar();
  }

  protected abrirNova(): void {
    this.abrirDialogo();
  }

  protected editar(servico: Servico): void {
    this.abrirDialogo(servico);
  }

  protected excluir(servico: Servico): void {
    const referencia = this.dialog.open<DialogoConfirmacao, DadosConfirmacao, boolean>(
      DialogoConfirmacao,
      {
        width: '400px',
        data: {
          titulo: 'Excluir serviço',
          mensagem: `Deseja realmente excluir o serviço "${servico.nomeServico}"?`,
        },
      },
    );

    referencia.afterClosed().subscribe((confirmado) => {
      if (!confirmado) {
        return;
      }

      this.servicoService.deletar(servico.id).subscribe({
        next: () => {
          this.notificacao.sucesso('Serviço excluído com sucesso.');
          this.carregar();
        },
        error: (erro: unknown) => this.notificacao.erro(extrairMensagemErro(erro)),
      });
    });
  }

  private abrirDialogo(servico?: Servico): void {
    const referencia = this.dialog.open<DialogoServico, DadosServicoDialogo, boolean>(
      DialogoServico,
      {
        width: '520px',
        data: { servico, categorias: this.categorias() },
      },
    );

    referencia.afterClosed().subscribe((salvo) => {
      if (salvo) {
        this.notificacao.sucesso('Serviço salvo com sucesso.');
        this.carregar();
      }
    });
  }

  private carregar(): void {
    this.carregando.set(true);

    forkJoin({
      servicos: this.servicoService.listar().pipe(listaOuVazia()),
      categorias: this.categoriaService.listar().pipe(listaOuVazia()),
    }).subscribe({
      next: ({ servicos, categorias }) => {
        this.servicos.set(servicos);
        this.categorias.set(categorias);
        this.carregando.set(false);
      },
      error: (erro: unknown) => {
        this.carregando.set(false);
        this.notificacao.erro(extrairMensagemErro(erro));
      },
    });
  }
}
