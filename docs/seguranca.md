# 1 Introdução

Esta documentação demonstra os papeis do sistema e quais estão com as restrições de autorização por _endpoint_, nítidos ao acessar a Documentação Swagger.

# 2 Como acessar Swagger UI

Ao realizar o passo de execução dentro do [README do projeto](/README.md#3-executar-o-back-end) para dar o deploy do back-end, pode-se acessar o endereço [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) para ver os _endpoints_ da plataforma.

## 2.1 Autenticação do usuário

Escolha entre cadastrar barbeiro e/ou cliente e vá para o _endpoint_ de `/autenticacao/login` para receber o token criado. Após isso, vá para o botão de _Authorize_ para desbloquear as funcionalidades de cada _endpoint_ atrelado aos papéis.

# 3 Papéis e restrições por endpoint

## 3.1 Papéis disponíveis

O sistema possui três papéis cadastrados em `backend/src/main/resources/data.sql`:

| Papel | Origem | Quem recebe hoje |
|:--|:--|:--|
| `CLIENTE` | `data.sql` e `ClienteService` | Autocadastro em `POST /clientes` (ID fixo `1`) |
| `BARBEIRO` | `data.sql` e `BarbeiroService` | Autocadastro em `POST /barbeiro` (ID fixo `2`) |
| `ADMIN` | `data.sql` | Nenhum fluxo de cadastro ou atribuição; não é usado em nenhuma regra de autorização |

O papel é uma entidade (`Papel`) que implementa `GrantedAuthority`. Cada usuário possui apenas um papel (`Usuario.papel`), e a autoridade retornada é exatamente o texto `CLIENTE`, `BARBEIRO` ou `ADMIN`, sem o prefixo `ROLE_`.

## 3.2 Endpoints e restrições

| Endpoint | Função | Restrição de autorização | Agente ou entidade atrelado |
|:--|:--|:--|:--|
| `POST /autenticacao/login` | Autenticar e gerar JWT | Público (`permitAll`) | Usuário (qualquer papel) |
| `POST /clientes` | Cadastrar cliente | Público (`permitAll`) | Cliente / `Usuario` e `Cliente` |
| `PUT /clientes/editar/informacoes/cliente` | Atualizar dados do cliente logado | `CLIENTE` | Cliente autenticado |
| `PUT /clientes/editar/informacoes/usuario` | Atualizar nome de usuário logado | `CLIENTE` | Cliente e usuário autenticado |
| `GET /clientes/barbeiros/{barbeiroId}/horarios-disponiveis` | Listar horários disponíveis de um barbeiro | `CLIENTE` | Cliente consultando `Barbeiro` e `Horario` |
| `POST /barbeiro` | Cadastrar barbeiro | Público (`permitAll`) | Barbeiro / `Usuario` e `Barbeiro` |
| `GET /categorias` | Listar categorias | `BARBEIRO` | Barbeiro / `Categoria` |
| `POST /categorias` | Cadastrar categoria | `BARBEIRO` | Barbeiro / `Categoria` |
| `GET /categorias/{id}` | Visualizar categoria | `BARBEIRO` | Barbeiro / `Categoria` |
| `PUT /categorias/{id}` | Atualizar categoria | `BARBEIRO` | Barbeiro / `Categoria` |
| `DELETE /categorias/{id}` | Deletar categoria | `BARBEIRO` | Barbeiro / `Categoria` |
| `GET /servicos` | Listar serviços do barbeiro logado | `BARBEIRO` | Barbeiro / `Servicos` |
| `POST /servicos` | Cadastrar serviço | `BARBEIRO` | Barbeiro / `Servicos` |
| `GET /servicos/{id}` | Visualizar serviço | `BARBEIRO` | Barbeiro / `Servicos` |
| `PUT /servicos/{id}` | Atualizar serviço | `BARBEIRO` | Barbeiro / `Servicos` |
| `DELETE /servicos/{id}` | Deletar serviço | `BARBEIRO` | Barbeiro / `Servicos` |
| `POST /horarios` | Cadastrar horário | `BARBEIRO` | Barbeiro / `Horario` |
| `GET /horarios` | Listar horários do barbeiro logado | `BARBEIRO` | Barbeiro / `Horario` |
| `PUT /horarios/{horarioId}` | Editar horário | `BARBEIRO` | Barbeiro / `Horario` |
| `DELETE /horarios/{horarioId}` | Excluir horário | `BARBEIRO` | Barbeiro / `Horario` |
| `POST /teste` | Persistir token de confirmação | Qualquer usuário autenticado | Usuário (`TesteController`) |
| `/h2-console/**` | Console H2 | Público (`permitAll`) | Infraestrutura |
| `/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs/**` | Documentação Swagger | Público (`permitAll`) | Infraestrutura |

## 433 Como as restrições estão sendo aplicadas

A autorização é feita exclusivamente por URL em `SecurityConfig`, por meio de `authorizeHttpRequests` com `requestMatchers(...)`. Não há `@EnableMethodSecurity`, `@PreAuthorize`, `@Secured` ou `@RolesAllowed` em nenhum controller; portanto, a proteção dos endpoints depende da configuração do `SecurityFilterChain`, e não de anotações nos métodos.

O fluxo de autenticação e autorização ocorre da seguinte forma:

1. `POST /autenticacao/login` autentica o usuário por meio do `AuthenticationManager` e gera um JWT que contém apenas o nome de usuário como `subject` (`TokenJwtService`).
2. Em cada requisição, o `FiltroJwt` lê o header `Authorization: Bearer <token>`, valida o token, busca o `Usuario` no banco e monta um `UsernamePasswordAuthenticationToken` com as authorities do usuário.
3. As authorities vêm de `Usuario.getAuthorities()`, que retorna o `Papel` associado ao usuário.
4. O `SecurityConfig` compara essas authorities com `hasAuthority("CLIENTE")` e `hasAuthority("BARBEIRO")`. Como `Papel.getAuthority()` devolve exatamente `CLIENTE` ou `BARBEIRO`, o match funciona.
5. Rotas não listadas explicitamente caem em `anyRequest().authenticated()`, exigindo apenas um usuário autenticado, sem exigir papel específico. É o caso de `POST /teste`.

## 4 Observações importantes

- **`ADMIN` não tem restrição efetiva**: nenhum matcher usa `ADMIN`, e não há endpoint para criar ou atribuir esse papel. Um usuário `ADMIN` autenticado acessaria apenas rotas cobertas por `anyRequest().authenticated()`; as rotas de `CLIENTE` e `BARBEIRO` o rejeitariam.
- **`/barbeiro` só tem o `POST` público**. Se novos endpoints forem adicionados em `/barbeiro/**`, eles não herdam `BARBEIRO`; cairão em `anyRequest().authenticated()`.
- **`@SecurityRequirement(name = "bearer")` é apenas documentação Swagger**, não aplica segurança.
- **Papéis fixos por ID**: os serviços usam `Long.valueOf(1)` e `Long.valueOf(2)`, dependendo da ordem do `data.sql`. Se os IDs mudarem, os cadastros passam a associar o papel errado.
- **Autorização por posse não fica no `SecurityConfig`**: para serviços e horários, os services verificam o barbeiro logado (`HorarioService` e `ServicosService`). Já `Categoria` é global, então qualquer barbeiro autenticado pode alterar ou excluir categorias de todos.

