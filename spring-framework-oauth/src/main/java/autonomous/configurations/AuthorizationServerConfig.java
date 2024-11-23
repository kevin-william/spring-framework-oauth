package autonomous.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class AuthorizationServerConfig {

  @Bean
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(DataSource dataSource) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); // Converte o DataSource para JdbcOperations
    return new JdbcRegisteredClientRepository(jdbcTemplate);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        .issuer("http://localhost:8080") // URL do Authorization Server
        .build();
  }

  @Bean
  public TokenSettings tokenSettings() {
    return TokenSettings.builder()
        .accessTokenTimeToLive(Duration.ofHours(1)) // Tempo de vida do token de acesso
        .reuseRefreshTokens(false) // Força o refresh token a ser regenerado
        .build();
  }
}

