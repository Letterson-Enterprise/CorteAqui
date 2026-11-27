import { HttpErrorResponse } from '@angular/common/http';

export function extrairMensagemErro(erro: unknown): string {
  if (erro instanceof HttpErrorResponse) {
    const corpo = erro.error;

    if (typeof corpo === 'string' && corpo.trim()) {
      return corpo;
    }

    if (corpo && typeof corpo === 'object') {
      const registro = corpo as Record<string, unknown>;

      for (const chave of ['mensagem', 'message', 'error']) {
        const valor = registro[chave];
        if (typeof valor === 'string' && valor.trim()) {
          return valor;
        }
      }

      const primeiraMensagem = Object.values(registro).find(
        (valor) => typeof valor === 'string' && valor.trim(),
      );
      if (typeof primeiraMensagem === 'string') {
        return primeiraMensagem;
      }
    }

    if (erro.status === 403) {
      return 'Acesso negado para o seu perfil.';
    }
    if (erro.status === 0) {
      return 'Não foi possível conectar ao servidor.';
    }

    return `Erro ${erro.status}: ${erro.statusText || 'requisição inválida'}`;
  }

  return 'Ocorreu um erro inesperado.';
}
