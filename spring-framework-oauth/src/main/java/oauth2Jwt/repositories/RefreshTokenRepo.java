package oauth2Jwt.repositories;

import oauth2Jwt.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Long> {
  Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
  @Query(value = "SELECT rt.* FROM REFRESH_TOKENS rt " +
      "INNER JOIN USER_DETAILS ud ON rt.user_id = ud.id " +
      "WHERE ud.EMAIL = :userEmail and rt.revoked = false ", nativeQuery = true)
  List<RefreshTokenEntity> findAllRefreshTokenByUserEmailId(String userEmail);
}
