package autonomous.configurations;

import autonomous.utils.FileHelper;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {

  @Bean
  public KeyPair rsaKeyPair() throws Exception {
    // Carrega as chaves RSA de arquivos PEM
    RSAPrivateKey privateKey = loadPrivateKey("C:\\Users\\lord_\\OneDrive\\Documentos\\workspace\\java\\springboot\\oauth-sample\\spring-framework-oauth\\spring-framework-oauth\\src\\main\\resources\\private_key.pem");
    RSAPublicKey publicKey = loadPublicKey("C:\\Users\\lord_\\OneDrive\\Documentos\\workspace\\java\\springboot\\oauth-sample\\spring-framework-oauth\\spring-framework-oauth\\src\\main\\resources\\public_key.pem");
    return new KeyPair(publicKey, privateKey);
  }

  private RSAPrivateKey loadPrivateKey(String path) throws Exception {

    String fileContent = FileHelper.getFileContent(path);

    String key = fileContent.replaceAll("-----\\w+ PRIVATE KEY-----", "").replaceAll("\\s+", "");
    byte[] decoded = Base64.getDecoder().decode(key);
    KeyFactory factory = KeyFactory.getInstance("RSA");
    return (RSAPrivateKey) factory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
  }

    private RSAPublicKey loadPublicKey(String path) throws Exception {
    String fileContent = FileHelper.getFileContent(path);
    String key = fileContent.replaceAll("-----\\w+ PUBLIC KEY-----", "").replaceAll("\\s+", "");
    byte[] decoded = Base64.getDecoder().decode(key);
    KeyFactory factory = KeyFactory.getInstance("RSA");
    return (RSAPublicKey) factory.generatePublic(new X509EncodedKeySpec(decoded));
  }

  @Bean
  public JwtEncoder jwtEncoder(KeyPair rsaKeyPair) {
    JWK jwk = new RSAKey.Builder((RSAPublicKey) rsaKeyPair.getPublic())
        .privateKey((RSAPrivateKey) rsaKeyPair.getPrivate())
        .keyID("key-id") // Identificador único para a chave
        .build();
    JWKSet jwkSet = new JWKSet(jwk);
    JWKSource<SecurityContext> jwkSource = (jwkSelector, context) -> jwkSelector.select(jwkSet);
    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  public JwtDecoder jwtDecoder(KeyPair rsaKeyPair) {
    return NimbusJwtDecoder.withPublicKey((RSAPublicKey) rsaKeyPair.getPublic()).build();
  }
}
