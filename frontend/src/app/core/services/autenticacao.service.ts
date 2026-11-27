import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  CadastroBarbeiroRequest,
  CadastroClienteRequest,
  LoginRequest,
  TokenResposta,
} from '../models/autenticacao.model';
import { Papel } from '../models/papel.model';
import { SessaoService } from './sessao.service';

@Injectable({ providedIn: 'root' })
export class AutenticacaoService {
  private readonly http = inject(HttpClient);
  private readonly sessaoService = inject(SessaoService);
  private readonly apiUrl = environment.apiUrl;

  login(credenciais: LoginRequest, papel: Papel): Observable<TokenResposta> {
    return this.http.post<TokenResposta>(`${this.apiUrl}/autenticacao/login`, credenciais).pipe(
      tap((resposta) =>
        this.sessaoService.criarSessao(resposta.token, papel, environment.duracaoTokenSegundos),
      ),
    );
  }

  cadastrarCliente(dados: CadastroClienteRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/clientes`, dados, { responseType: 'text' });
  }

  cadastrarBarbeiro(dados: CadastroBarbeiroRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/barbeiro`, dados, { responseType: 'text' });
  }

  logout(): void {
    this.sessaoService.encerrarSessao();
  }
}
