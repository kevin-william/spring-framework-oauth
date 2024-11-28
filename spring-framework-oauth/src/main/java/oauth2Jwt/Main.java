package oauth2Jwt;

import oauth2Jwt.configurations.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class Main {
  public static void main(String[] args){
    System.out.println("Hello world!");
    SpringApplication.run(Main.class,args);
  }

}