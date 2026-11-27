import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';

export interface DadosConfirmacao {
  titulo: string;
  mensagem: string;
}

@Component({
  selector: 'app-dialogo-confirmacao',
  imports: [MatDialogModule, MatButtonModule],
  template: `
    <h2 mat-dialog-title>{{ dados.titulo }}</h2>
    <mat-dialog-content>{{ dados.mensagem }}</mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button type="button" (click)="cancelar()">Cancelar</button>
      <button mat-flat-button color="warn" type="button" (click)="confirmar()">
        Confirmar
      </button>
    </mat-dialog-actions>
  `,
})
export class DialogoConfirmacao {
  protected readonly dados = inject<DadosConfirmacao>(MAT_DIALOG_DATA);
  private readonly referencia = inject(MatDialogRef<DialogoConfirmacao>);

  protected cancelar(): void {
    this.referencia.close(false);
  }

  protected confirmar(): void {
    this.referencia.close(true);
  }
}
