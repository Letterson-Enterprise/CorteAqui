import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { PerfilService } from '../../core/services/perfil.service';
import { NotificacaoService } from '../../shared/services/notificacao.service';
import { extrairMensagemErro } from '../../shared/utils/erro';

@Component({
  selector: 'app-perfil',
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './perfil.html',
  styleUrl: './perfil.css',
})
export class Perfil {
  private readonly perfilService = inject(PerfilService);
  private readonly notificacao = inject(NotificacaoService);
  private readonly formularioBuilder = inject(NonNullableFormBuilder);

  protected readonly salvandoDados = signal(false);
  protected readonly salvandoUsuario = signal(false);

  protected readonly formularioDados = this.formularioBuilder.group({
    nome: ['', [Validators.required]],
    sobreNome: ['', [Validators.required]],
  });

  protected readonly formularioUsuario = this.formularioBuilder.group({
    nomeUsuario: ['', [Validators.required]],
  });

  protected salvarDados(): void {
    if (this.formularioDados.invalid) {
      this.formularioDados.markAllAsTouched();
      return;
    }

    this.salvandoDados.set(true);
    this.perfilService.atualizarCliente(this.formularioDados.getRawValue()).subscribe({
      next: () => {
        this.salvandoDados.set(false);
        this.notificacao.sucesso('Dados pessoais atualizados com sucesso.');
        this.formularioDados.reset();
      },
      error: (erro: unknown) => {
        this.salvandoDados.set(false);
        this.notificacao.erro(extrairMensagemErro(erro));
      },
    });
  }

  protected salvarUsuario(): void {
    if (this.formularioUsuario.invalid) {
      this.formularioUsuario.markAllAsTouched();
      return;
    }

    this.salvandoUsuario.set(true);
    this.perfilService.atualizarUsuario(this.formularioUsuario.getRawValue()).subscribe({
      next: () => {
        this.salvandoUsuario.set(false);
        this.notificacao.sucesso('Nome de usuário atualizado com sucesso.');
        this.formularioUsuario.reset();
      },
      error: (erro: unknown) => {
        this.salvandoUsuario.set(false);
        this.notificacao.erro(extrairMensagemErro(erro));
      },
    });
  }
}
