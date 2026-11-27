import { Injectable, computed, signal } from '@angular/core';
import { Papel } from '../models/papel.model';
import { Sessao } from '../models/autenticacao.model';

const CHAVE_SESSAO = 'corteAqui.sessao';

@Injectable({ providedIn: 'root' })
export class SessaoService {
  private readonly sessaoSignal = signal<Sessao | null>(this.lerSessao());

  readonly sessao = this.sessaoSignal.asReadonly();
  readonly papel = computed<Papel | null>(() => this.sessaoSignal()?.papel ?? null);
  readonly estaAutenticado = computed(() => {
    const sessao = this.sessaoSignal();
    return !!sessao && sessao.expiracao > Date.now();
  });

  criarSessao(token: string, papel: Papel, duracaoSegundos: number): void {
    const sessao: Sessao = {
      token,
      papel,
      expiracao: Date.now() + duracaoSegundos * 1000,
    };
    localStorage.setItem(CHAVE_SESSAO, JSON.stringify(sessao));
    this.sessaoSignal.set(sessao);
  }

  encerrarSessao(): void {
    localStorage.removeItem(CHAVE_SESSAO);
    this.sessaoSignal.set(null);
  }

  obterToken(): string | null {
    return this.sessaoSignal()?.token ?? null;
  }

  private lerSessao(): Sessao | null {
    const valor = localStorage.getItem(CHAVE_SESSAO);
    if (!valor) {
      return null;
    }

    try {
      const sessao = JSON.parse(valor) as Sessao;
      return sessao.expiracao > Date.now() ? sessao : null;
    } catch {
      return null;
    }
  }
}
