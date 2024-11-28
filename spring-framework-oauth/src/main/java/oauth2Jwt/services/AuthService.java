package oauth2Jwt.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import oauth2Jwt.configurations.jwt.JwtTokenGenerator;
import oauth2Jwt.dtos.AuthResponseDto;
import oauth2Jwt.dtos.TokenType;
import oauth2Jwt.entities.RefreshTokenEntity;
import oauth2Jwt.entities.UserInfoEntity;
import oauth2Jwt.repositories.RefreshTokenRepo;
import oauth2Jwt.repositories.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  private final UserInfoRepo userInfoRepo;
  private final JwtTokenGenerator jwtTokenGenerator;
  private final RefreshTokenRepo refreshTokenRepo;

  public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response) {
    try
    {
      var userInfoEntity = userInfoRepo.findByEmailId(authentication.getName())
          .orElseThrow(()->{
            log.error("[AuthService:userSignInAuth] User :{} not found",authentication.getName());
            return new ResponseStatusException(HttpStatus.NOT_FOUND,"USER NOT FOUND ");});


      String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
      String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

      saveUserRefreshToken(userInfoEntity,refreshToken);

      creatRefreshTokenCookie(response,refreshToken);

      log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",userInfoEntity.getUserName());
      return  AuthResponseDto.builder()
          .accessToken(accessToken)
          .accessTokenExpiry(15 * 60)
          .userName(userInfoEntity.getUserName())
          .tokenType(TokenType.Bearer)
          .build();
    }catch (Exception e){
      log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :"+e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please Try Again");
    }
  }

  private void saveUserRefreshToken(UserInfoEntity userInfoEntity, String refreshToken) {
    var refreshTokenEntity = RefreshTokenEntity.builder()
        .user(userInfoEntity)
        .refreshToken(refreshToken)
        .revoked(false)
        .build();
    refreshTokenRepo.save(refreshTokenEntity);
  }

  private Cookie creatRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
    Cookie refreshTokenCookie = new Cookie("refresh_token",refreshToken);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setSecure(true);
    refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60 ); // in seconds
    response.addCookie(refreshTokenCookie);
    return refreshTokenCookie;
  }

  public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {

    if(!authorizationHeader.startsWith(TokenType.Bearer.name())){
      return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please verify your token type");
    }

    final String refreshToken = authorizationHeader.substring(7);

    //Find refreshToken from database and should not be revoked : Same thing can be done through filter.
    var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
        .filter(tokens-> !tokens.isRevoked())
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Refresh token revoked"));

    UserInfoEntity userInfoEntity = refreshTokenEntity.getUser();

    //Now create the Authentication object
    Authentication authentication =  createAuthenticationObject(userInfoEntity);

    //Use the authentication object to generate new accessToken as the Authentication object that we will have may not contain correct role.
    String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

    return  AuthResponseDto.builder()
        .accessToken(accessToken)
        .accessTokenExpiry(5 * 60)
        .userName(userInfoEntity.getUserName())
        .tokenType(TokenType.Bearer)
        .build();
  }

  private static Authentication createAuthenticationObject(UserInfoEntity userInfoEntity) {
    // Extract user details from UserDetailsEntity
    String username = userInfoEntity.getEmailId();
    String password = userInfoEntity.getPassword();
    String roles = userInfoEntity.getRoles();

    // Extract authorities from roles (comma-separated)
    String[] roleArray = roles.split(",");
    GrantedAuthority[] authorities = Arrays.stream(roleArray)
        .map(role -> (GrantedAuthority) role::trim)
        .toArray(GrantedAuthority[]::new);

    return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
  }

}

