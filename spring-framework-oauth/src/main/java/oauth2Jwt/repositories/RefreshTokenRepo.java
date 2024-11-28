package oauth2Jwt.repositories;

import oauth2Jwt.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Long> {
  Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
}
