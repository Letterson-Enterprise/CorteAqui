import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Observable } from 'rxjs';
import { Horario, HorarioRequest } from '../../../core/models/horario.model';
import { HorarioService } from '../../../core/services/horario.service';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';

export interface DadosHorarioDialogo {
  horario?: Horario;
}

@Component({
  selector: 'app-dialogo-horario',
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  template: `
    <h2 mat-dialog-title>{{ editando ? 'Editar horário' : 'Novo horário' }}</h2>

    <form [formGroup]="formulario" (ngSubmit)="salvar()">
      <mat-dialog-content class="conteudo">
        <div class="linha">
          <mat-form-field appearance="outline">
            <mat-label>Data</mat-label>
            <input matInput type="date" formControlName="dataHorario" />
            @if (formulario.controls.dataHorario.hasError('required')) {
              <mat-error>Informe a data.</mat-error>
            }
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Hora</mat-label>
            <input matInput type="time" formControlName="hora" />
            @if (formulario.controls.hora.hasError('required')) {
              <mat-error>Informe a hora.</mat-error>
            }
          </mat-form-field>
        </div>
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
        padding-top: 0.5rem;
      }
      .linha {
        display: flex;
        gap: 0.75rem;
      }
      .linha mat-form-field {
        flex: 1;
      }
    `,
  ],
})
export class DialogoHorario {
  private readonly dados = inject<DadosHorarioDialogo>(MAT_DIALOG_DATA);
  private readonly referencia = inject(MatDialogRef<DialogoHorario>);
  private readonly horarioService = inject(HorarioService);
  private readonly notificacao = inject(NotificacaoService);
  private readonly formularioBuilder = inject(NonNullableFormBuilder);

  protected readonly editando = !!this.dados.horario;
  protected readonly salvando = signal(false);
  protected readonly formulario = this.formularioBuilder.group({
    dataHorario: [this.dados.horario?.dataHorario ?? '', [Validators.required]],
    hora: [this.dados.horario ? this.dados.horario.hora.slice(0, 5) : '', [Validators.required]],
  });

  protected salvar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.salvando.set(true);
    const dados = this.formulario.getRawValue() as HorarioRequest;
    const requisicao: Observable<unknown> =
      this.editando && this.dados.horario
        ? this.horarioService.editar(this.dados.horario.id, dados)
        : this.horarioService.cadastrar(dados);

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
