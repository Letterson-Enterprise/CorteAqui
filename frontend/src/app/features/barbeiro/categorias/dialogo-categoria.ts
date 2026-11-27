import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Observable } from 'rxjs';
import { Categoria } from '../../../core/models/categoria.model';
import { CategoriaService } from '../../../core/services/categoria.service';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';

export interface DadosCategoriaDialogo {
  categoria?: Categoria;
}

@Component({
  selector: 'app-dialogo-categoria',
  imports: [ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  template: `
    <h2 mat-dialog-title>{{ editando ? 'Editar categoria' : 'Nova categoria' }}</h2>

    <form [formGroup]="formulario" (ngSubmit)="salvar()">
      <mat-dialog-content class="conteudo">
        <mat-form-field appearance="outline">
          <mat-label>Nome</mat-label>
          <input matInput formControlName="nome" />
          @if (formulario.controls.nome.hasError('required')) {
            <mat-error>Informe o nome.</mat-error>
          } @else if (formulario.controls.nome.hasError('minlength')) {
            <mat-error>O nome deve ter ao menos 3 caracteres.</mat-error>
          }
        </mat-form-field>

        <mat-form-field appearance="outline">
          <mat-label>Descrição</mat-label>
          <textarea matInput formControlName="descricao" rows="3"></textarea>
          @if (formulario.controls.descricao.hasError('required')) {
            <mat-error>Informe a descrição.</mat-error>
          } @else if (formulario.controls.descricao.hasError('maxlength')) {
            <mat-error>A descrição deve ter no máximo 200 caracteres.</mat-error>
          }
        </mat-form-field>
      </mat-dialog-content>

      <mat-dialog-actions align="end">
        <button mat-button type="button" mat-dialog-close>Cancelar</button>
        <button mat-flat-button color="primary" type="submit" [disabled]="salvando()">
          Salvar
        </button>
      </mat-dialog-actions>
    </form>
  `,
  styles: [
    `
      .conteudo {
        display: flex;
        flex-direction: column;
        gap: 0.5rem;
        padding-top: 0.5rem;
      }
    `,
  ],
})
export class DialogoCategoria {
  private readonly dados = inject<DadosCategoriaDialogo>(MAT_DIALOG_DATA);
  private readonly referencia = inject(MatDialogRef<DialogoCategoria>);
  private readonly categoriaService = inject(CategoriaService);
  private readonly notificacao = inject(NotificacaoService);
  private readonly formularioBuilder = inject(NonNullableFormBuilder);

  protected readonly editando = !!this.dados.categoria;
  protected readonly salvando = signal(false);
  protected readonly formulario = this.formularioBuilder.group({
    nome: [
      this.dados.categoria?.nome ?? '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(100)],
    ],
    descricao: [
      this.dados.categoria?.descricao ?? '',
      [Validators.required, Validators.maxLength(200)],
    ],
  });

  protected salvar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.salvando.set(true);
    const dados = this.formulario.getRawValue();
    const requisicao: Observable<unknown> =
      this.editando && this.dados.categoria
        ? this.categoriaService.atualizar(this.dados.categoria.id, dados)
        : this.categoriaService.cadastrar(dados);

    requisicao.subscribe({
      next: () => {
        this.salvando.set(false);
        this.referencia.close(true);
      },
      error: (erro: unknown) => {
        this.salvando.set(false);
        this.notificacao.erro(extrairMensagemErro(erro));
      },
    });
  }
}
