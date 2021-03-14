package coursework.prostranstvo.Repository;

import coursework.prostranstvo.Model.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> getVerificationTokenByToken(String token);
}
