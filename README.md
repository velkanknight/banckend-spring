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
git clone https://gitlab.com/regisflorentino/springstack.git
```

- Acessar a pasta para realizar o build do projeto:

- Fazer o build do projeto:

```
mvn clean install
```

- Acessar diretório para executar a aplicação:

```
cd springstack
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

## Mysql

### Instalação via docker-compose

1 - Crie um arquivo docker-compose.yml com o conteúdo:

version: '2'
services:
  mysql_db:
    image: mysql:latest
    volumes:
      - "./.mysql-data/db:/var/lib/mysql"
    restart: always
    ports:
      - 3306:3306
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: testedb
      MYSQL_USER: user
      MYSQL_PASSWORD: user

2 - Execute sudo docker-compose up -d no mesmo diretório que se localiza o arquivo. 
	Será feito o download da imagem do docker e será inicializada. 

### Acessar Mysql

    Voce pode acessar o mysql via temrinal, mas sugiro que baixa o my-workbench, instale e aponte para o seu localhost:3306
    Crie o banco testedb para que seja criada a tabela e relizado a persistencia


## Swagger

### Acessando via Swagger

Acesse as informações abaixo:

- Url: http://localhost:8081/swagger-ui.html
- Utilize os recursos para fazer chamadas as apis