# ✂️ Corte Aqui

<div align="center">

### Sistema de Agendamento para Barbeiros Autônomos

Plataforma para facilitar o agendamento de serviços em barbearias, permitindo que clientes consultem horários disponíveis e agendem atendimentos, enquanto barbeiros organizam sua agenda de trabalho.

![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-orange?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.8-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-22-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.x-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)

</div>

---

## 📜 Descrição

O **Corte Aqui** é um sistema de agendamento de serviços voltado para barbeiros autônomos que desejam organizar seus horários de atendimento e facilitar o agendamento por parte de seus clientes.

A proposta do sistema é centralizar o gerenciamento da agenda em uma plataforma digital, reduzindo a dependência de conversas por aplicativos de mensagens ou agendamentos realizados verbalmente.

Por meio do sistema, os clientes poderão consultar os horários disponíveis e realizar seus agendamentos de forma prática, enquanto os barbeiros poderão administrar seus horários de trabalho e acompanhar os atendimentos programados.

O projeto encontra-se em fase inicial de desenvolvimento, e suas funcionalidades estão sendo planejadas para implementação.

## 🎯 Objetivo

Simplificar o processo de agendamento de serviços de barbearia, proporcionando maior organização para os profissionais e mais autonomia para os clientes.

O sistema busca reduzir dificuldades relacionadas à disponibilidade de horários, aos cancelamentos e aos reagendamentos, tornando o gerenciamento dos atendimentos mais eficiente.

## 📌 Funcionalidades previstas

### 🗓️ Agendamentos e agenda

- Visualização dos horários disponíveis para atendimento.
- Realização de agendamentos pelos clientes.
- Cadastro, consulta, edição e exclusão dos horários de trabalho pelo barbeiro.
- Organização dos atendimentos agendados.
- Cancelamento e reagendamento de horários, conforme as funcionalidades previstas para o sistema.

### 👤 Clientes

- Cadastro de usuários com nome, e-mail e senha.
- Autenticação para acesso ao sistema.
- Consulta dos horários disponíveis.
- Agendamento de serviços.
- Recebimento de um token de confirmação por e-mail.
- Possibilidade de cancelamento de agendamentos.

### 💈 Barbeiros

- Autenticação para acesso ao sistema.
- Gerenciamento dos dias e horários de trabalho.
- Consulta da agenda de atendimentos.
- Cadastro e gerenciamento de categorias de serviços.
- Confirmação do comparecimento do cliente por meio de um token associado ao agendamento.

### 🔔 Notificações e organização dos atendimentos

- Geração de tokens de confirmação após a realização de um agendamento.
- Gerenciamento dos agendamentos por meio de uma fila de prioridades.
- Envio de notificações relacionadas à possibilidade de reagendamento.
- Sugestão de horários disponíveis para clientes que precisem reagendar seus atendimentos.

> **Observação:** as funcionalidades descritas representam o escopo planejado do projeto. Elas ainda não estão implementadas e poderão ser ajustadas durante o desenvolvimento.

## 🧰 Stack de Tecnologias

### Back-End

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 | Linguagem utilizada no desenvolvimento do back-end. |
| Spring Boot | 4.0.8 | Base para o desenvolvimento da aplicação e disponibilização dos serviços. |
| Spring Web | Compatível com a versão do Spring Boot | Desenvolvimento dos endpoints da API REST. |
| Spring Data JPA | Compatível com a versão do Spring Boot | Integração com a camada de persistência. |
| Hibernate | Gerenciada pelas dependências do projeto | Mapeamento objeto-relacional (ORM). |

### Front-End

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Angular | 22 | Desenvolvimento da interface web. |
| TypeScript | Versão compatível com Angular 22 | Linguagem utilizada no desenvolvimento do front-end. |
| Node.js | Versão compatível com Angular 22 | Ambiente de execução utilizado pelas ferramentas de desenvolvimento. |

### Banco de Dados

| Tecnologia | Finalidade |
|---|---|
| PostgreSQL | Sistema gerenciador de banco de dados relacional para armazenamento das informações da aplicação. |

### Ferramentas de desenvolvimento

- **Maven:** gerenciamento de dependências e construção do back-end.
- **Angular CLI:** criação, configuração e execução do front-end.
- **Git e GitHub:** controle de versão e hospedagem do código-fonte.

*As versões exatas das dependências e das ferramentas auxiliares serão definidas nos respectivos arquivos de configuração durante a implementação.*

## 💻 Como Utilizar

Como o projeto ainda está em desenvolvimento, as instruções abaixo descrevem os requisitos e a forma prevista de utilização. Os comandos definitivos de execução serão adicionados após a configuração das aplicações.

### Pré-requisitos

Para preparar o ambiente de desenvolvimento, serão necessários:

- Java Development Kit (JDK) 21.
- Node.js em uma versão compatível com Angular 22.
- Angular CLI compatível com Angular 22.
- PostgreSQL instalado e configurado.
- Maven ou Maven Wrapper para o back-end.
- Git para obter o código-fonte do projeto.

### 1. Obter o código-fonte

Clone o repositório e acesse o diretório do projeto:

```bash
git clone <URL_DO_REPOSITORIO>
cd <DIRETORIO_DO_PROJETO>
```

Substitua os valores entre os sinais de menor e maior pelas informações do repositório.

### 2. Configurar o banco de dados

Instale e inicie o PostgreSQL. Após a implementação da aplicação, configure as credenciais de acesso e os parâmetros de conexão no back-end, utilizando as configurações definidas para o ambiente de execução.

### 3. Executar o back-end

Após a configuração do projeto Spring Boot e de suas dependências, a aplicação poderá ser iniciada com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

No Windows, utilize:

```bash
mvnw.cmd spring-boot:run
```

O endereço da API e as configurações necessárias para sua execução serão documentados após a implementação.

### 4. Executar o front-end

Após a criação e configuração do projeto Angular, instale as dependências:

```bash
npm install
```

Em seguida, inicie o servidor de desenvolvimento:

```bash
ng serve
```

O endereço local de acesso à interface será informado quando a aplicação estiver configurada.

## 🗂️ Organização do Projeto

A estrutura definitiva dos diretórios será documentada conforme a implementação do back-end e do front-end.

A organização prevista contempla duas aplicações principais:

- **Back-end:** aplicação Java com Spring Boot, responsável pelos serviços e pelas operações relacionadas aos agendamentos.
- **Front-end:** aplicação Angular, responsável pela interface utilizada por clientes e barbeiros.

## 🚧 Status do Projeto

O Corte Aqui está em fase de planejamento e início de desenvolvimento.

As tecnologias principais foram definidas, e o escopo funcional contempla o gerenciamento de horários de trabalho, o agendamento de serviços, a confirmação de atendimentos e os recursos de cancelamento e reagendamento.

As instruções de instalação, configuração e utilização serão atualizadas à medida que as funcionalidades forem implementadas.

## 🤝 Contribuição

Durante o desenvolvimento, contribuições poderão ser realizadas por meio do fluxo habitual de colaboração com Git:

1. Faça um fork do repositório.
2. Crie uma branch para sua alteração.
3. Implemente a funcionalidade ou correção.
4. Envie suas alterações para o repositório remoto.
5. Abra um Pull Request para revisão.

## 📝 Licença

A licença do projeto será definida e documentada durante o desenvolvimento.
