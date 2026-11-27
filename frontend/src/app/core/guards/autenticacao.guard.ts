import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SessaoService } from '../services/sessao.service';

export const autenticacaoGuard: CanActivateFn = () => {
  const sessaoService = inject(SessaoService);
  const router = inject(Router);

  if (sessaoService.estaAutenticado()) {
    return true;
  }

  sessaoService.encerrarSessao();
  return router.createUrlTree(['/entrar']);
};
