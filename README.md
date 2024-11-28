### **README: Aplicação Spring Boot com OAuth2**

**Visão Geral**

Esta aplicação Spring Boot demonstra a implementação de autenticação e autorização OAuth2 utilizando o Spring Security. Ela fornece uma configuração básica para proteger APIs REST com OAuth2.

**Pré-requisitos**

  * **Java Development Kit (JDK):** Versão 11 ou superior
  * **Ferramenta de Build:** Maven ou Gradle
  * **Banco de Dados:** Um banco de dados relacional como MySQL, PostgreSQL ou Oracle (opcional, dependendo da sua implementação)
  * **Servidor de Autorização OAuth2:** Um servidor de autorização OAuth2 configurado (por exemplo, Keycloak, Auth0 ou uma implementação personalizada)

**Iniciando**

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/seu-projeto.git
    ```
2.  **Construa o projeto:**
      * **Maven:**
        ```bash
        mvn clean install
        ```
      * **Gradle:**
        ```bash
        ./gradlew build
        ```
3.  **Configure o cliente OAuth2:**
      * Obtenha o ID do cliente, segredo do cliente e URL do servidor de autorização do seu provedor OAuth2.
      * Atualize o arquivo `application.properties` com essas credenciais.
4.  **Execute a aplicação:**
      * **Maven:**
        ```bash
        mvn spring-boot:run
        ```
      * **Gradle:**
        ```bash
        ./gradlew bootRun
        ```

**Estrutura do Projeto**

```
seu-projeto/
├── src/main/java/
│   ├── com/example/demo/
│   │   ├── SecurityConfig.java
│   │   ├── UserController.java
│   │   └── ...
├── src/main/resources/
│   └── application.properties
├── pom.xml (ou build.gradle)
```

**Componentes-chave**

  * **Configuração de Segurança:**
      * Configura o Spring Security para usar OAuth2 para autenticação e autorização.
      * Define os detalhes do cliente OAuth2 e a configuração do servidor de recursos.
  * **Detalhes do Cliente OAuth2:**
      * Armazena o ID do cliente, segredo do cliente e outras informações necessárias para a comunicação com o servidor de autorização.
  * **Configuração do Servidor de Recursos:**
      * Configura o servidor de recursos para proteger endpoints específicos.
      * Define regras de controle de acesso com base em papéis ou escopos.
  * **Endpoints Protegidos:**
      * Controladores que exigem autenticação e autorização.
      * O acesso a esses endpoints é restrito a usuários autenticados e autorizados.

**Configuração**

O arquivo `application.properties` deve conter as seguintes propriedades:

```properties
spring.security.oauth2.client.registration.oauth2client.client-id=seu_client_id
spring.security.oauth2.client.registration.oauth2client.client-secret=seu_client_secret
# ... outras propriedades
```
