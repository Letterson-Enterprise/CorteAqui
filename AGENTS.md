---
    Nome Do Arquivo: AGENTS.md
    Descrição: Arquivo para guiar agentes de código para auxiliar o desenvolimento,
---

## Descrição do projeto
Este é um projeto web fullstack chamado corteAqui, seu proposito está em facilitar os processos de agendamentos entre cliente e um barbeiro. Seu foco está em ajudar barbeiros que trabalham de forma solo mas que também pode ajudar em uma barbearia com diversos barbeiros. 

## Tecnologias utilizadas
1. Java 21
2. Spring boot 4.0.8
3. Node.js 22.x
4. Typescript 5.x
5. Angular 22
4. PostgreSQL 18

## Arquitetura Back-End
```text
backend/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── br/
    │   │       └── com/
    │   │           └── corteAqui/
    │   │               └── backend/
    │   │                   ├── components/
    │   │                   ├── config/
    │   │                   ├── controllers/
    │   │                   ├── dtos/
    │   │                   │   ├── request/
    │   │                   │   └── response/
    │   │                   ├── enums/
    │   │                   ├── exceptions/
    │   │                   ├── models/
    │   │                   ├── repositories/
    │   │                   └── services/
    │   └── resources/
    │       ├── static/
    │       └── templates/
    └── test/
        └── java/
            └── br/
                └── com/
                    └── corteAqui/
                        └── backend/
```

## Arquitetura Front-End
```text
frontend/
├── angular.json
├── package.json
├── public/
│   └── favicon.ico
├── README.md
├── src/
│   ├── app/
│   │   ├── core/
│   │   ├── shared/
│   │   ├── features/
│   │   ├── app.config.ts
│   │   ├── app.css
│   │   ├── app.html
│   │   ├── app.routes.ts
│   │   ├── app.spec.ts
│   │   └── app.ts
│   ├── index.html
│   ├── main.ts
│   └── styles.css
├── tsconfig.app.json
├── tsconfig.json
└── tsconfig.spec.json
```

## Instruções
1. Utilize sempre nomes em português para o código.
2. Utilize camelCase para o código do projeto.
3. Utilize nomes descritivos para nomes de variaveis, metodos, e classes.
4. Utilize verbos para nomes de metodos, preferencialmente.
5. Utilize a separação que foi implementada em cada uma das estruturas do projeto.
6. Utilize records para criar DTO`s no backend do projeto.
7. Para nomes de endpoits, siga o dominio que está sendo trabalhado, em casos de mais especificidade pode ser utilizado no endpoint.
8. Sempre verifique como está o estado atual do projeto para compreensão do mesmo.
9. Utilize sempre o conventional commit para realizar os commits.
10. Nunca faça push direto para a branch main.

## Exemplos de código

1. Código Java 

```Java
@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(agedamentoService.listarTodosAgendamentos());
    }
}
```

2. Código Typescript/Angular
```typescript
@Injectable({ providedIn: 'root' })
export class AutenticacaoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/auth`;

  private readonly token = signal<string | null>(localStorage.getItem(TOKEN_KEY));

  readonly estaAutenticado = computed(() => !!this.token() && !this.isExpired());

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponsta>(`${this.apiUrl}/login`, credentials).pipe(
      tap((res) => this.criarSession(res)),
    );
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(EXPIRACAO_KEY);
    this.token.set(null);
  }

  getToken(): string | null {
    return this.token();
  }

  private criarSession(res: LoginResponse): void {
    localStorage.setItem(TOKEN_KEY, res.token);
    localStorage.setItem(EXPIRACAO_KEY, res.dataExpiracao);
    this.token.set(res.token);
  }

  private estaExpirado(): boolean {
    const expiracao = localStorage.getItem(EXPIRACAO_KEY);
    if (!expiracao) return true;
    return new Date(expiracao).getTime() <= Date.now();
  }
}
```

## Exemplo de commit
1. Commit seguindo o conventional commit

```text
:sparkles: feat: Implemetando operações crud de agendamento e realização do serviço de envio de email.
```

## Exemplo de branch
1. Padrão de branch

```text
feature/CorteAqui-005/Implementar_gerenciamento_de_servico_e_categorias
```
