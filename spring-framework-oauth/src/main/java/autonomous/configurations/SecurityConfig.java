package autonomous.configurations;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

  private final UserInfoManagerConfig userInfoManagerConfig;


  @Order(1)
  @Bean
  public SecurityFilterChain signInSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/sign-in/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .userDetailsService(userInfoManagerConfig)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(ex -> {
          ex.authenticationEntryPoint((request, response, authException) ->
              response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()));
        })
        .httpBasic(Customizer.withDefaults())
        .build();
  }

  @Order(1)
  @Bean
  public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/api/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .userDetailsService(userInfoManagerConfig)
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
//  @Order(2)
//  @Bean
//  public SecurityFilterChain h2ConsoleSecurityFilterChainConfig(HttpSecurity httpSecurity) throws Exception{
//    return httpSecurity
//        .securityMatcher(new AntPathRequestMatcher(("/h2-console/**")))
//        .authorizeHttpRequests(auth->auth.anyRequest().permitAll())
//        .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
//        // to display the h2Console in Iframe
//        .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
//        .build();
//  }

}
