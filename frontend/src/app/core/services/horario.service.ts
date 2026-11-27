import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Horario, HorarioRequest } from '../models/horario.model';

@Injectable({ providedIn: 'root' })
export class HorarioService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/horarios`;

  listarPorBarbeiro(): Observable<Horario[]> {
    return this.http.get<Horario[]>(this.apiUrl);
  }

  cadastrar(dados: HorarioRequest): Observable<string> {
    return this.http.post(this.apiUrl, dados, { responseType: 'text' });
  }

  editar(id: number, dados: HorarioRequest): Observable<string> {
    return this.http.put(`${this.apiUrl}/${id}`, dados, { responseType: 'text' });
  }

  excluir(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }

  listarDisponiveis(barbeiroId: number): Observable<Horario[]> {
    return this.http.get<Horario[]>(
      `${environment.apiUrl}/clientes/barbeiros/${barbeiroId}/horarios-disponiveis`,
    );
  }
}
