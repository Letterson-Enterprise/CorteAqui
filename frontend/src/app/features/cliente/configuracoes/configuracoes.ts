import { Component, inject } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { PrototipoConfigService } from '../../../core/config/prototipo.config';
import { NotificacaoService } from '../../../shared/services/notificacao.service';

@Component({
  selector: 'app-configuracoes',
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './configuracoes.html',
  styleUrl: './configuracoes.css',
})
export class Configuracoes {
  private readonly configuracao = inject(PrototipoConfigService);
  private readonly notificacao = inject(NotificacaoService);
  private readonly formularioBuilder = inject(NonNullableFormBuilder);

  protected readonly formulario = this.formularioBuilder.group({
    barbeiroId: [this.configuracao.barbeiroId(), [Validators.required, Validators.min(1)]],
    servicoId: [this.configuracao.servicoId(), [Validators.required, Validators.min(1)]],
  });

  protected salvar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const { barbeiroId, servicoId } = this.formulario.getRawValue();
    this.configuracao.definirBarbeiroId(barbeiroId);
    this.configuracao.definirServicoId(servicoId);
    this.notificacao.sucesso('Configurações salvas com sucesso.');
  }
}
