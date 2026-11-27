import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AgendamentoRequest } from '../models/agendamento.model';

@Injectable({ providedIn: 'root' })
export class AgendamentoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/agendamentos`;

  agendar(idHorario: number, dados: AgendamentoRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/${idHorario}`, dados, { responseType: 'text' });
  }
}
