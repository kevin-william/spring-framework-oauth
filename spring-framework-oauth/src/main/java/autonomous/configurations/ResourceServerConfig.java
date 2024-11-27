package autonomous.configurations;

import autonomous.utils.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {

  @Autowired
  private JwtDecoder jwtDecoder;

  @Bean
  public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> {
        authz.requestMatchers("/public").permitAll();// Endpoint público
        authz.requestMatchers("/").permitAll();// Endpoint público
//      authz.requestMatchers("/login").permitAll() // Endpoint público
        authz.requestMatchers("/user").hasRole("USER"); // Requer ROLE_USER
        authz.requestMatchers("/admin").hasRole("ADMIN"); // Requer ROLE_ADMIN

        authz.requestMatchers("/tasks/*").authenticated(); // Outros endpoints requerem autenticação
        authz.requestMatchers("/tasks").authenticated(); // Outros endpoints requerem autenticação
        authz.anyRequest().authenticated(); // Outros endpoints requerem autenticação
      }

        ).formLogin(form -> form
            .successHandler((request, response, authentication) -> {
              response.setContentType("application/json");
              response.getWriter().write("{\"status\": \"Login bem-sucedido\"}");
            }))
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> {
          jwtConfigurer.decoder(jwtDecoder); // Configura o JwtDecoder manualmente
        })); // Habilita validação JWT
    return http.build();
  }

//  @Bean
//  public JwtDecoder jwtDecoder() throws NoSuchAlgorithmException, InvalidKeySpecException {
//    return NimbusJwtDecoder.withPublicKey(loadPublicKey()).build();
//  }

//  private RSAPublicKey loadPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
//    // Carregar a chave pública do arquivo public.pem gerado anteriormente
//    try {
//      String path = "C:\\Users\\lord_\\OneDrive\\Documentos\\workspace\\java\\springboot\\oauth-sample\\spring-framework-oauth\\spring-framework-oauth\\src\\main\\resources\\public_key.pem";
//      String fileContent = FileHelper.getFileContent(path);
//      String key = fileContent.replaceAll("-----\\w+ PUBLIC KEY-----", "").replaceAll("\\s+", "");
//
//      byte[] decoded = Base64.getDecoder().decode(key);
//      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//      return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(decoded));
//    } catch (Exception ex) {
//      throw new RuntimeException("Erro ao carregar chave pública", ex);
//    }
//  }
}
