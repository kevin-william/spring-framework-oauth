package autonomous.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .authorizeHttpRequests(authz -> authz
//            .anyRequest().authenticated()
//        )
//        .formLogin(Customizer.withDefaults()); // Login baseado em formulário
//    return http.build();
//  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Usuários em memória para teste
  @Bean
  public UserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
    var user = org.springframework.security.core.userdetails.User.builder()
        .username("user")
        .password(passwordEncoder.encode("user"))
        .roles("USER")
        .build();

    var admin = org.springframework.security.core.userdetails.User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("ADMIN","USER")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }
}

