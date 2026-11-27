import { Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';

const CHAVE_BARBEIRO = 'corteAqui.barbeiroId';
const CHAVE_SERVICO = 'corteAqui.servicoId';

@Injectable({ providedIn: 'root' })
export class PrototipoConfigService {
  private readonly barbeiroIdSignal = signal<number>(
    this.lerNumero(CHAVE_BARBEIRO, environment.barbeiroIdPadrao),
  );
  private readonly servicoIdSignal = signal<number>(
    this.lerNumero(CHAVE_SERVICO, environment.servicoIdPadrao),
  );

  readonly barbeiroId = this.barbeiroIdSignal.asReadonly();
  readonly servicoId = this.servicoIdSignal.asReadonly();

  definirBarbeiroId(valor: number): void {
    this.barbeiroIdSignal.set(valor);
    localStorage.setItem(CHAVE_BARBEIRO, String(valor));
  }

  definirServicoId(valor: number): void {
    this.servicoIdSignal.set(valor);
    localStorage.setItem(CHAVE_SERVICO, String(valor));
  }

  private lerNumero(chave: string, padrao: number): number {
    const valor = localStorage.getItem(chave);
    const numero = valor ? Number(valor) : NaN;
    return Number.isFinite(numero) && numero > 0 ? numero : padrao;
  }
}
