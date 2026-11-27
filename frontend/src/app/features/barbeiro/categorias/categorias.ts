import { Component, OnInit, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule } from '@angular/material/table';
import { Categoria } from '../../../core/models/categoria.model';
import { CategoriaService } from '../../../core/services/categoria.service';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import {
  DadosConfirmacao,
  DialogoConfirmacao,
} from '../../../shared/components/dialogo-confirmacao/dialogo-confirmacao';
import { extrairMensagemErro } from '../../../shared/utils/erro';
import { listaOuVazia } from '../../../shared/utils/lista';
import { DadosCategoriaDialogo, DialogoCategoria } from './dialogo-categoria';

@Component({
  selector: 'app-categorias',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatProgressBarModule,
  ],
  templateUrl: './categorias.html',
  styleUrl: '../crud.css',
})
export class Categorias implements OnInit {
  private readonly categoriaService = inject(CategoriaService);
  private readonly dialog = inject(MatDialog);
  private readonly notificacao = inject(NotificacaoService);

  protected readonly carregando = signal(true);
  protected readonly categorias = signal<Categoria[]>([]);
  protected readonly colunas = ['nome', 'descricao', 'acoes'];

  ngOnInit(): void {
    this.carregar();
  }

  protected abrirNova(): void {
    this.abrirDialogo();
  }

  protected editar(categoria: Categoria): void {
    this.abrirDialogo(categoria);
  }

  protected excluir(categoria: Categoria): void {
    const referencia = this.dialog.open<DialogoConfirmacao, DadosConfirmacao, boolean>(
      DialogoConfirmacao,
      {
        width: '400px',
        data: {
          titulo: 'Excluir categoria',
          mensagem: `Deseja realmente excluir a categoria "${categoria.nome}"?`,
        },
      },
    );

    referencia.afterClosed().subscribe((confirmado) => {
      if (!confirmado) {
        return;
      }

      this.categoriaService.deletar(categoria.id).subscribe({
        next: () => {
          this.notificacao.sucesso('Categoria excluída com sucesso.');
          this.carregar();
        },
        error: (erro: unknown) => this.notificacao.erro(extrairMensagemErro(erro)),
      });
    });
  }

  private abrirDialogo(categoria?: Categoria): void {
    const referencia = this.dialog.open<DialogoCategoria, DadosCategoriaDialogo, boolean>(
      DialogoCategoria,
      {
        width: '480px',
        data: { categoria },
      },
    );

    referencia.afterClosed().subscribe((salvo) => {
      if (salvo) {
        this.notificacao.sucesso('Categoria salva com sucesso.');
        this.carregar();
      }
    });
  }

  private carregar(): void {
    this.carregando.set(true);
    this.categoriaService
      .listar()
      .pipe(listaOuVazia())
      .subscribe({
        next: (categorias) => {
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
