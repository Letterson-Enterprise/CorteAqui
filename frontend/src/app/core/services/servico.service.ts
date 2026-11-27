import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Servico, ServicoRequest } from '../models/servico.model';

@Injectable({ providedIn: 'root' })
export class ServicoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/servicos`;

  listar(): Observable<Servico[]> {
    return this.http.get<Servico[]>(this.apiUrl);
  }

  visualizar(id: number): Observable<Servico> {
    return this.http.get<Servico>(`${this.apiUrl}/${id}`);
  }

  cadastrar(dados: ServicoRequest): Observable<string> {
    return this.http.post(this.apiUrl, dados, { responseType: 'text' });
  }

  atualizar(id: number, dados: ServicoRequest): Observable<Servico> {
    return this.http.put<Servico>(`${this.apiUrl}/${id}`, dados);
  }

  deletar(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }
}
