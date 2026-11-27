import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Papel } from '../models/papel.model';
import { SessaoService } from '../services/sessao.service';

export function papelGuard(papelEsperado: Papel): CanActivateFn {
  return () => {
    const sessaoService = inject(SessaoService);
    const router = inject(Router);

    if (!sessaoService.estaAutenticado()) {
      return router.createUrlTree(['/entrar']);
    }

    if (sessaoService.papel() !== papelEsperado) {
      const destino = sessaoService.papel() === 'BARBEIRO' ? '/barbeiro/painel' : '/cliente/agendar';
      return router.createUrlTree([destino]);
    }

    return true;
  };
}
