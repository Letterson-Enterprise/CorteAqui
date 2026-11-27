import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router, RouterLink } from '@angular/router';
import { AutenticacaoService } from '../../../core/services/autenticacao.service';
import { NotificacaoService } from '../../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../../shared/utils/erro';
import { PADRAO_SENHA, senhasIguaisValidator } from '../../../shared/validators/senha.validator';

@Component({
  selector: 'app-cadastro-cliente',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './cadastro-cliente.html',
  styleUrl: '../cadastro.css',
})
export class CadastroCliente {
  private readonly formularioBuilder = inject(NonNullableFormBuilder);
  private readonly autenticacaoService = inject(AutenticacaoService);
  private readonly notificacao = inject(NotificacaoService);
  private readonly router = inject(Router);

  protected readonly carregando = signal(false);
  protected readonly formulario = this.formularioBuilder.group(
    {
      nome: ['', [Validators.required]],
      sobreNome: ['', [Validators.required]],
      nomeUsuario: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required, Validators.minLength(8), Validators.pattern(PADRAO_SENHA)]],
      confirmarSenha: ['', [Validators.required]],
    },
    { validators: senhasIguaisValidator() },
  );

  protected cadastrar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const { nome, sobreNome, nomeUsuario, email, senha } = this.formulario.getRawValue();
    this.carregando.set(true);

    this.autenticacaoService.cadastrarCliente({ nome, sobreNome, nomeUsuario, email, senha }).subscribe({
      next: () => {
        this.carregando.set(false);
        this.notificacao.sucesso('Cadastro realizado com sucesso. Faça login para continuar.');
        void this.router.navigate(['/entrar']);
      },
      error: (erro: unknown) => {
        this.carregando.set(false);
        this.notificacao.erro(extrairMensagemErro(erro));
      },
    });
  }
}
