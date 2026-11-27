import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { SessaoService } from '../services/sessao.service';

export const tokenInterceptor: HttpInterceptorFn = (requisicao, proximo) => {
  const sessaoService = inject(SessaoService);
  const router = inject(Router);

  const token = sessaoService.obterToken();
  const requisicaoAutenticada = token
    ? requisicao.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : requisicao;

  return proximo(requisicaoAutenticada).pipe(
    catchError((erro: HttpErrorResponse) => {
      if (erro.status === 401) {
        sessaoService.encerrarSessao();
        void router.navigate(['/entrar']);
      }
      return throwError(() => erro);
    }),
  );
};
