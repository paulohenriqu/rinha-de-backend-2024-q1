
# Rinha de Backend, Segunda Edição: 2024/Q1

## Endpoints

Transações
Requisição

POST /clientes/[id]/transacoes

```
{
"valor": 1000,
"tipo" : "c",
"descricao" : "descricao"
}
```
Onde

- id (na URL) deve ser um número inteiro representando a identificação do cliente.
- valor deve um número inteiro positivo que representa centavos (não vamos trabalhar com frações de centavos). Por exemplo, R$ 10 são 1000 centavos.
- tipo deve ser apenas c para crédito ou d para débito.
- descricao deve ser uma string de 1 a 10 caractéres.
Todos os campos são obrigatórios.

Resposta

HTTP 200 OK

```
{
"limite" : 100000,
"saldo" : -9098
}
```
Onde

- limite deve ser o limite cadastrado do cliente.
- saldo deve ser o novo saldo após a conclusão da transação.


Extrato
Requisição

GET /clientes/[id]/extrato

Onde

- [id] (na URL) deve ser um número inteiro representando a identificação do cliente.
Resposta

HTTP 200 OK

```
{
"saldo": {
"total": -9098,
"data_extrato": "2024-01-17T02:34:41.217753Z",
"limite": 100000
},
"ultimas_transacoes": [
{
"valor": 10,
"tipo": "c",
"descricao": "descricao",
"realizada_em": "2024-01-17T02:34:38.543030Z"
},
{
"valor": 90000,
"tipo": "d",
"descricao": "descricao",
"realizada_em": "2024-01-17T02:34:38.543030Z"
}
]
}
```
Onde

- saldo
  - total deve ser o saldo total atual do cliente (não apenas das últimas transações seguintes exibidas).
  - data_extrato deve ser a data/hora da consulta do extrato.
  - limite deve ser o limite cadastrado do cliente.
- ultimas_transacoes é uma lista ordenada por data/hora das transações de forma decrescente contendo até as 10 últimas transações com o seguinte:
  - valor deve ser o valor da transação.
  - tipo deve ser c para crédito e d para débito.
  - descricao deve ser a descrição informada durante a transação.
  - realizada_em deve ser a data/hora da realização da transação.

## Tecnologias utilizadas:
- Spring Boot
- Spring WebFlux
- PostgreSQL
- R2dbc - non-blocking Postgres driver
- Imagem nativa GraalVM

## Banco de dados

PostgreSQL com conexão reativa, a conexão pode ser configurada com as seguintes properties no arquivo application.properties

    spring.r2dbc.url=r2dbc:postgresql://localhost:5432/rinha
    spring.r2dbc.username=postgres
    spring.r2dbc.password=postgres

Ou sobrescrevendo pelas variáveis de ambiente:

    SPRING_R2DBC_URL
    SPRING_R2DBC_USERNAME
    SPRING_R2DBC_PASSWORD
Os scripts para inicializar a database e popular os dados iniciais estão no arquivo [init.sql](init.sql)

## Rodando localmente

Use o comando

`mvn spring-boot:run`

A porta padrão é a 8080

## Compilando o projeto
Para compilar uma imagem nativa da aplicação é necessário ter a GraalVM configurada e setada como JDK padrão. Após isso é possível usar o comando
`mvn -Pnative native:compile`

## Docker
Para gerar uma imagem do Docker com a imagem nativa criada basta usar o [Dockerfile](Dockerfile) na raiz do projeto e exwecutar o comando
`docker buildx build --platform linux/amd64 -t phs/rinha-webflux:latest .`

Após isso rodar com
`docker run -p 8080:8080 --network="host" phs/rinha-webflux:latest`

Também existe a opção de usar a imagem nativa gerada pelo plugin do Spring Boot:
`mvn -Pnative spring-boot:build-image`

O arquivo [docker-compose.yaml](docker-compose.yaml) contém todas as dependências necessárias para o projeto ser executado.