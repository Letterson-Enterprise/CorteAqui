import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import {
  MatButtonToggleChange,
  MatButtonToggleModule,
} from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router, RouterLink } from '@angular/router';
import { Papel } from '../../../core/models/papel.model';
import { AutenticacaoService } from '../../../core/services/autenticacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';
import { NotificacaoService } from '../../../shared/services/notificacao.service';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatIconModule,
    MatDividerModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private readonly formularioBuilder = inject(NonNullableFormBuilder);
  private readonly autenticacaoService = inject(AutenticacaoService);
  private readonly notificacao = inject(NotificacaoService);
  private readonly router = inject(Router);

  protected readonly papelSelecionado = signal<Papel>('CLIENTE');
  protected readonly carregando = signal(false);
  protected readonly formulario = this.formularioBuilder.group({
    nomeUsuario: ['', [Validators.required]],
    senha: ['', [Validators.required]],
  });

  protected aoMudarPapel(evento: MatButtonToggleChange): void {
    this.papelSelecionado.set(evento.value as Papel);
  }

  protected entrar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const papel = this.papelSelecionado();
    this.carregando.set(true);

    this.autenticacaoService.login(this.formulario.getRawValue(), papel).subscribe({
      next: () => {
        this.carregando.set(false);
        const destino = papel === 'BARBEIRO' ? '/barbeiro/painel' : '/cliente/agendar';
        void this.router.navigate([destino]);
      },
      error: (erro: unknown) => {
        this.carregando.set(false);
        this.notificacao.erro(extrairMensagemErro(erro));
      },
    });
  }
}
