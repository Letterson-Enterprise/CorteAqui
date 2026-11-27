import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Observable } from 'rxjs';
import { Categoria } from '../../../core/models/categoria.model';
import { Servico } from '../../../core/models/servico.model';
import { ServicoService } from '../../../core/services/servico.service';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';

export interface DadosServicoDialogo {
  servico?: Servico;
  categorias: Categoria[];
}

@Component({
  selector: 'app-dialogo-servico',
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
  ],
  template: `
    <h2 mat-dialog-title>{{ editando ? 'Editar serviço' : 'Novo serviço' }}</h2>

    <form [formGroup]="formulario" (ngSubmit)="salvar()">
      <mat-dialog-content class="conteudo">
        @if (dados.categorias.length === 0) {
          <p class="aviso">Cadastre uma categoria antes de criar serviços.</p>
        }

        <mat-form-field appearance="outline">
          <mat-label>Nome do serviço</mat-label>
          <input matInput formControlName="nomeServico" />
          @if (formulario.controls.nomeServico.hasError('required')) {
            <mat-error>Informe o nome do serviço.</mat-error>
          } @else if (formulario.controls.nomeServico.hasError('minlength')) {
            <mat-error>O nome deve ter ao menos 5 caracteres.</mat-error>
          }
        </mat-form-field>

        <div class="linha">
          <mat-form-field appearance="outline">
            <mat-label>Preço (R$)</mat-label>
            <input matInput type="number" min="1" step="0.01" formControlName="preco" />
            @if (formulario.controls.preco.hasError('required') || formulario.controls.preco.hasError('min')) {
              <mat-error>Informe um preço válido.</mat-error>
            }
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Categoria</mat-label>
            <mat-select formControlName="idCategoria">
              @for (categoria of dados.categorias; track categoria.id) {
                <mat-option [value]="categoria.id">{{ categoria.nome }}</mat-option>
              }
            </mat-select>
            @if (formulario.controls.idCategoria.hasError('required') || formulario.controls.idCategoria.hasError('min')) {
              <mat-error>Selecione uma categoria.</mat-error>
            }
          </mat-form-field>
        </div>

        @if (editando) {
          <p class="aviso">A categoria não é carregada na edição; selecione novamente.</p>
        }
      </mat-dialog-content>

      <mat-dialog-actions align="end">
        <button mat-button type="button" mat-dialog-close>Cancelar</button>
        <button
          mat-flat-button
          color="primary"
          type="submit"
          [disabled]="salvando() || dados.categorias.length === 0"
        >
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
      .linha {
        display: flex;
        gap: 0.75rem;
      }
      .linha mat-form-field {
        flex: 1;
      }
      .aviso {
        margin: 0 0 0.5rem;
        font-size: 0.8rem;
        color: var(--mat-sys-on-surface-variant);
      }
    `,
  ],
})
export class DialogoServico {
  private readonly dados = inject<DadosServicoDialogo>(MAT_DIALOG_DATA);
  private readonly referencia = inject(MatDialogRef<DialogoServico>);
  private readonly servicoService = inject(ServicoService);
  private readonly notificacao = inject(NotificacaoService);
  private readonly formularioBuilder = inject(NonNullableFormBuilder);

  protected readonly editando = !!this.dados.servico;
  protected readonly salvando = signal(false);
  protected readonly formulario = this.formularioBuilder.group({
    nomeServico: [
      this.dados.servico?.nomeServico ?? '',
      [Validators.required, Validators.minLength(5), Validators.maxLength(255)],
    ],
    preco: [this.dados.servico?.preco ?? 0, [Validators.required, Validators.min(1)]],
    idCategoria: [this.dados.categorias[0]?.id ?? 0, [Validators.required, Validators.min(1)]],
  });

  protected salvar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.salvando.set(true);
    const dados = this.formulario.getRawValue();
    const requisicao: Observable<unknown> =
      this.editando && this.dados.servico
        ? this.servicoService.atualizar(this.dados.servico.id, dados)
        : this.servicoService.cadastrar(dados);

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
