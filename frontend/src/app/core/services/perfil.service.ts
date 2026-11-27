import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface AtualizacaoClienteRequest {
  nome: string;
  sobreNome: string;
}

export interface AtualizacaoUsuarioRequest {
  nomeUsuario: string;
}

@Injectable({ providedIn: 'root' })
export class PerfilService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/clientes/editar/informacoes`;

  atualizarCliente(dados: AtualizacaoClienteRequest): Observable<string> {
    return this.http.put(`${this.apiUrl}/cliente`, dados, { responseType: 'text' });
  }

  atualizarUsuario(dados: AtualizacaoUsuarioRequest): Observable<string> {
    return this.http.put(`${this.apiUrl}/usuario`, dados, { responseType: 'text' });
  }
}
