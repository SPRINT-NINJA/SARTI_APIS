package mx.edu.utez.SARTI_APIS.modules.verification_code.model;

import mx.edu.utez.SARTI_APIS.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    int deleteByUser(User user);

    //find by email and code
    Optional<VerificationCode> findByUserAndCode(User user, String code);
}
