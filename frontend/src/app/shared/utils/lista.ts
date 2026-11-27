import { HttpErrorResponse } from '@angular/common/http';
import { OperatorFunction, catchError, of, throwError } from 'rxjs';

export function listaOuVazia<T>(): OperatorFunction<T[], T[]> {
  return catchError((erro: unknown) => {
    if (erro instanceof HttpErrorResponse && erro.status === 404) {
      return of([] as T[]);
    }
    return throwError(() => erro);
  });
}
