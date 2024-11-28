package oauth2Jwt.controllers;

import oauth2Jwt.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  @PostMapping("/sign-in")
  public ResponseEntity<?> authenticateUser(Authentication authentication){

    return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication));
  }
}
