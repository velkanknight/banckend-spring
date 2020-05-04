## Começando

As instruções apresentadas abaixo servirão de base para que o projeto entre em execução em uma máquina local para fins de desenvolvimento e testes.

### Pré-requisitos

Para que o microsserviço funcione corretamente, será necessário instalar:  

```
- Java 8;
- Git;
- Maven (a versão utilizada pode ser consultada no arquivo pom.xml);
- Mysql;
- Lombok;
```

### Instalando

Passo a passo de execução para que o microsserviço fique em execução e disponível para uso.


- Realizar o clone do projeto (inserir seu usuário):

```
https://github.com/velkanknight/banckend-spring.git
```

- Acessar a pasta para realizar o build do projeto:

- Fazer o build do projeto:

```
mvn clean install
```

- Acessar diretório para executar a aplicação:

```
cd banckend-spring
```

- Subir a aplicação:

```
mvn spring-boot:run
```

# Instalando Ferramentas Auxiliares

### Instalação via docker

- [Primeira opção]: Acesse: https://bitbucket.org/vrbeneficiosteam/docker-rabbitmq/
  Obs.: Caso queira alterar o usuário e senha do Rabbit adicione as linhas dentro de enviorment no arquivo docker-compose.yml:
  version: '3'

  services:
    rabbitmq:
      image: "rabbitmq:3-management"
      restart: on-failure
      ports:
        - "5672:5672"
        - "15672:15672"
      environment:
        RABBITMQ_DEFAULT_USER: admin
        RABBITMQ_DEFAULT_PASS: admin

- [Segunda opção]: `sudo docker run -d --restart always --hostname localhost  --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:3-management`

## Postgres

### Instalação via docker-compose

1 - Crie um arquivo docker-compose.yml com o conteúdo:

# Use postgres/example user/password credentials
#user: postgres pass: ''
version: '3.1'

services:

  db:
    image: postgres
    restart: always
    environment:
      - POSTGRES_PASSWORD=secrect
      - POSTGRES_USER=postgres

  adminer:
    image: adminer
    restart: always
    ports:
      - 8082:8080

2 - Execute sudo docker-compose up -d no mesmo diretório que se localiza o arquivo. 
	Será feito o download da imagem do docker e será inicializada. 


## Swagger

### Acessando via Swagger

Acesse as informações abaixo:

- Url: http://localhost:8081/swagger-ui.html
- Utilize os recursos para fazer chamadas as apis
