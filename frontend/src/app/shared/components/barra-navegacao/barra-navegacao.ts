import { Component, inject, output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { SessaoService } from '../../../core/services/sessao.service';

@Component({
  selector: 'app-barra-navegacao',
  imports: [MatToolbarModule, MatButtonModule, MatIconModule],
  template: `
    <mat-toolbar color="primary">
      <button
        mat-icon-button
        type="button"
        aria-label="Alternar menu"
        (click)="alternar.emit()"
      >
        <mat-icon>menu</mat-icon>
      </button>
      <span class="titulo">Corte Aqui</span>
      <span class="espaco"></span>
      <span class="papel">{{ papel() }}</span>
      <button mat-icon-button type="button" aria-label="Sair" (click)="sair()">
        <mat-icon>logout</mat-icon>
      </button>
    </mat-toolbar>
  `,
  styles: [
    `
      .titulo {
        margin-inline-start: 0.5rem;
        font-weight: 500;
      }
      .espaco {
        flex: 1 1 auto;
      }
      .papel {
        font-size: 0.8rem;
        opacity: 0.85;
        margin-inline-end: 0.5rem;
      }
    `,
  ],
})
export class BarraNavegacao {
  private readonly sessaoService = inject(SessaoService);
  private readonly router = inject(Router);

  readonly alternar = output<void>();
  readonly papel = this.sessaoService.papel;

  protected sair(): void {
    this.sessaoService.encerrarSessao();
    void this.router.navigate(['/entrar']);
  }
}
