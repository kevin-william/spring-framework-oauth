### **README: Aplicação Spring Boot com OAuth2**

**Visão Geral**

é um trabalho em progresso.
Atualmente a lógica já funciona para login, refresh token, logout, gestão de rotas por Roles e Scopes.

Os plano futuro é melhorar algumas regras, por exemplo a forma de como obter roles e scopes. 
Outro plano futuro é adicionar  

**Pré-requisitos**

  * **Java Development Kit (JDK):** Versão 23 ou superior
  * **Ferramenta de Build:** Maven
  * **Banco de Dados:** Um banco de dados relacional Oracle
  * **Servidor de Autorização OAuth2:** Um servidor de autorização OAuth2 configurado

3.  **Configure o cliente OAuth2:**
      * será atualizado
4.  **Execute a aplicação:**
      * **Maven:**
        ```bash
        mvn spring-boot:run
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

  * **Lore Ipsum:**
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
