# Sistema de Gestão de Monitorias
Sistema desenvolvido para gestão de monitorias acadêmicas, seu objetivo é conectar monitores 
e coordenadores no agendamento e acompanhamento de encontros de reforço escolar.

> 🎓 **Projeto acadêmico** desenvolvido no curso de Análise e Desenvolvimento de Sistemas da **UNINTER — Centro Universitário Internacional**.

### 🔗 Repositórios

* **Backend:** este repositório
* **Frontend:** [monitoria-front](https://github.com/RyanMartinsOne/monitoria-front)

O backend fornece a **API REST** utilizada pelo frontend para autenticação, gerenciamento de usuários, 
monitorias e estatísticas.


## 📌 Sobre o Projeto

O sistema permite que monitores e coordenadores gerenciem encontros de monitoria, organizando 
informações como:

- Matéria  
- Monitor responsável  
- Aluno beneficiado  
- Data e horário  
- Assunto  
- Status do encontro  
- Observações  

Além do gerenciamento dos encontros, o sistema possui autenticação de usuários e um dashboard para 
coordenadores com informações estatísticas.

## 🔧 Funcionalidades

### Autenticação e autorização
- Cadastro de usuários
- Login
- Autenticação utilizando JWT
- Controle de acesso baseado em perfil
- Perfis de Monitor e Coordenador
### Gerenciamento de monitorias
- Agendamento de encontros
- Consulta de encontros
- Edição de encontros
- Cancelamento de encontros
- Atualização do status
- Filtragem por matéria, status e monitor
### Dashboard
- Visualização de estatísticas dos encontros
- Total de monitorias
- Monitorias concluídas
- Monitorias agendadas
- Monitorias canceladas
- Taxa de conclusão
- Filtros para análise dos dados

## 💻 Tecnologias

### Backend
- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MapStruct
- Bean Validation
- Maven

### Banco de dados
- PostgreSQL
- Flyway

### Frontend
- React
- TypeScript
- Tailwind CSS

### Ferramentas
- Git
- GitHub
- Docker
- Caddy

## 🏗️ Arquitetura

A API foi desenvolvida utilizando uma arquitetura organizada em camadas:

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

### Principais responsabilidades

#### Controller

Responsável por receber as requisições HTTP e retornar as respostas da API.

#### Service

Concentra as regras de negócio da aplicação.

#### Repository

Responsável pelo acesso e persistência dos dados utilizando Spring Data JPA.

#### DTOs

Utilizados para controlar os dados recebidos e enviados pela API, evitando a exposição direta das entidades.

#### Mapper

O MapStruct é utilizado para realizar a conversão Entidades ⇔ DTOs.

## 📡 API

A aplicação disponibiliza uma API REST responsável pela comunicação entre o frontend e o backend.

Os endpoints são organizados por responsabilidade e utilizam **JSON** para envio e recebimento de dados. Os endpoints protegidos exigem autenticação por meio de **JWT**.

### 🔑 Autenticação

| Método | Endpoint         | Descrição                  | Autenticação |
| ------ | ---------------- | -------------------------- | ------------ |
| `POST` | `/auth/login`    | Realiza o login do usuário | Não          |
| `POST` | `/auth/register` | Cadastra um novo usuário   | Não          |

### 👤 Usuários

| Método | Endpoint       | Descrição                               | Autenticação |
| ------ | -------------- | --------------------------------------- | ------------ |
| `GET`  | `/usuarios/me` | Retorna os dados do usuário autenticado | JWT          |

### 📅 Encontros

| Método   | Endpoint          | Descrição                       | Autenticação |
| -------- | ----------------- | ------------------------------- | ------------ |
| `GET`    | `/encontros`      | Lista os encontros de monitoria | JWT          |
| `POST`   | `/encontros`      | Agenda um novo encontro         | JWT          |
| `PUT`    | `/encontros/{id}` | Atualiza um encontro            | JWT          |
| `DELETE` | `/encontros/{id}` | Remove um encontro              | JWT          |

### 📊 Dashboard

Disponível somente para usuários do tipo coordenador.

| Método | Endpoint                   | Descrição                               | Autenticação |
| ------ | -------------------------- | --------------------------------------- | ------------ |
| `GET`  | `/dashboard/encontros`      | Retorna os encontros de todos usuários | JWT          |
| `GET`  | `/dashboard/encontros/{id}` | Retorna o encontro especificado        | JWT          |
| `GET`  | `/dashboard/usuarios`      | Retorna todos os usuários cadastrados   | JWT          |
| `GET`  | `/dashboard/usuarios/{id}` | Retorna o usuário especificado          | JWT          |


### 🔒 Autorização

Os endpoints protegidos utilizam **JWT (JSON Web Token)**. O token é enviado no header da requisição:

```http
Authorization: Bearer <token>
```

O acesso às funcionalidades também pode variar de acordo com o perfil do usuário:

* **MONITOR:** acesso às funcionalidades relacionadas às monitorias da sua matéria.
* **COORDENADOR:** acesso às funcionalidades administrativas e ao dashboard.

## 🗄️ Banco de dados

O sistema utiliza PostgreSQL como banco de dados relacional.

As alterações no schema são controladas utilizando Flyway, permitindo versionar as migrações do banco de dados junto ao código da aplicação.

## ▶️ Como executar
Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java
- Maven
- PostgreSQL

### 1. Clone o repositório
```cmd
git clone https://github.com/RyanMartinsOne/Extensionista-Painel-Monitoria.git
```
### 2. Configure o banco de dados

Crie um banco de dados PostgreSQL e configure as informações de conexão no arquivo de configuração da aplicação.

### 3. Execute a aplicação

```cmd
./mvnw spring-boot:run
```

No Windows:

```cmd
mvnw.cmd spring-boot:run
```

A API estará disponível localmente em:

```http
http://localhost:8080
```

## 📚 Objetivos de aprendizagem

O desenvolvimento deste projeto permitiu aplicar conceitos como:

- Desenvolvimento de APIs REST
- Arquitetura em camadas
- Princípios de separação de responsabilidades
- Autenticação e autorização
- Persistência de dados com JPA
- Modelagem de banco de dados
- Versionamento de banco com Flyway
- Validação de dados
- Tratamento de exceções
- Integração entre frontend e backend
- Versionamento de código com Git
