### 1 Introdução
Este artefato visa documentar o passo a passo para a subida de containers docker para manipulação dos dados do sistema por meio do banco de dados e ferramenta de manipulação.

### 1.2 Requisitos

1. Docker
2. Docker Compose

### 2 Executar docker compose para Base de dados
Utilize o docker compose na raiz do projeto para ter uma base de dados postgreSQL e a ferramenta PgAdmin para banco de dados. Execute o comando abaixo na raiz do projeto.

```bash
docker compose up -d
```

Para verificar se os containers subiram, utilize o seguinte comando e espere uma saída como a imagem mostrada:

```bash
docker ps
```

![Saída listando containers](./imagens/Screenshot%20from%202026-11-21%2010-38-13.png)

### 2.2 Acessos para as aplicações vindas do docker
1. Usuário do banco: `postgres`
2. Senha do banco: `1234567`
3. E-mail para acesso ao PgAdmin: `me@example.com`
4. Senha para acesso ao PgAdmin: `1234567`
5. Nome do banco de dados: `CorteAquiDB`

### 2.3 Portas de funcionamento das aplicações vindas do docker

1. PostgreSQL: `5433` ou `5432`
2. PgAdmin: `5050`

### 3 Acesso ao PgAdmin
Essa seção irá mostrar como poderá ser configurado uma a conexão ao banco de dados e usos iniciais da ferramenta pgAdmin. Siga os seguintes passo abaixo:

![Acesso ao pgadmin](./imagens/Screenshot%20from%202026-11-21%2009-23-45.png)

Acesse por meio do navegador a URL `http://localhost:5050` ou `localhost:5050` conforme a imagem, após acessar a URL, a pagina de login do PgAdmin irá aparecer conforme a imagem a seguir.

![Página inicial pgAdmin](./imagens/Screenshot%20from%202026-11-21%2009-24-21.png)

Utilize as credenciais conforme a documentação para acessar a aplicação.


Após conectar, você poderá criar a conexão com o banco de dados utilizando as credenciais fornecidas do banco de dados as quais foram exibidas na documentação.

Clique com o botão direito do mouse em cima de `Servers` e procure a opção `Registra > Servidor...` conforme a imagem descrita abaixo.

![Caso de uso para registro de servidor](./imagens/Screenshot%20from%202026-11-21%2009-33-34.png)

Em seguida, comece as configurações para poder ser feito a conexão com o banco de dados. Faça os seguintes passos:

1. Defina o nome da conexão.
2. Definir o host da aplicação com `pg-docker`.
3. Definir a porta para `5433`.
4. Definir banco principal com `CorteAquiDB`.
5. Definir usuário como `postgres`.
6. Definir senha como `1234567`.

As imagens abaixo demonstram o passo a passo.

![Definição do nome da conexão](./imagens/Screenshot%20from%202026-11-21%2009-34-04.png)

![Denição das configurações restantes](./imagens/Screenshot%20from%202026-11-21%2009-35-00.png)

Após isso será possível acessar o banco de dados e manipulá-lo conforme a imagem abaixo:

![Acesso ao caso de uso de manipulação](./imagens/Screenshot%20from%202026-11-21%2009-37-57.png)

![Manipulação de dados](./imagens/Screenshot%20from%202026-11-21%2009-39-33.png)


### 4 Considerações finais
Com isso será possível realizar consultas e manipular qualquer dado no banco de dados. Mas, um adendo aqui, o banco de dados virá sem as tabelas, para que elas sejam criadas automaticamente, terá que ser definido e configurado o arquivo `application-dev.properties` na aplicação *Spring Boot*, para que a aplicação consiga realizar a conexão com o banco de dados. Após a configuração do arquivo, defina seu uso no arquivo `application.properties`ao qual provavelmente estará usando o ambiente de testes com o banco em cache H2. Então com as configurações feitas, ao subir a aplicação, as tabelas serão criadas automaticamente.