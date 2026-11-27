import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const PADRAO_SENHA = /^(?=.*[A-Z])(?=.*\d)(?=.*\W).*$/;

export function senhasIguaisValidator(): ValidatorFn {
  return (grupo: AbstractControl): ValidationErrors | null => {
    const senha = grupo.get('senha')?.value;
    const confirmarSenha = grupo.get('confirmarSenha')?.value;

    if (!senha || !confirmarSenha) {
      return null;
    }

    return senha === confirmarSenha ? null : { senhasDiferentes: true };
  };
}
