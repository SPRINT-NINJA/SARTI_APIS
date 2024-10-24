package mx.edu.utez.SARTI_APIS.modules.verification_code.service;

import mx.edu.utez.SARTI_APIS.kernel.ResponseApi;
import mx.edu.utez.SARTI_APIS.modules.auth.service.AuthMessages;
import mx.edu.utez.SARTI_APIS.modules.user.model.User;
import mx.edu.utez.SARTI_APIS.modules.verification_code.model.VerificationCode;
import mx.edu.utez.SARTI_APIS.utils.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.verification_code.model.VerificationCodeRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public ResponseApi<Optional<VerificationCode>> findVerificationCodeByEmailAndCode(User user, String code) {
        try{
            Optional<VerificationCode> verificationCode = verificationCodeRepository.findByUserAndCode(user, code);
            return new ResponseApi<>(verificationCode, HttpStatus.ACCEPTED, false, AuthMessages.VERIFICATION_CODE);
        } catch (Exception e) {
            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.VERIFICATION_CODE_NOT_FOUND);
        }
    }


    @Transactional(rollbackFor = {Exception.class})
    public ResponseApi<String> generateCode(String email, User userSaved) {
        try {
            verificationCodeRepository.deleteByUser(userSaved);

            String confirmationCode = String.format("%05d", new Random().nextInt(99999));
            VerificationCode verificationCode = new VerificationCode();
            verificationCode.setCode(confirmationCode);
            verificationCode.setUser(userSaved);
            verificationCodeRepository.save(verificationCode);

            String emailContent = "Tu código de confirmación es: " + confirmationCode;
            boolean emailSent = emailService.sendConfirmationEmail(userSaved.getEmail(), "Confirmación de cuenta", emailContent);

            if (!emailSent) {
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_SENDING_EMAIL);
            }
            return new ResponseApi<>(null, HttpStatus.OK, false, AuthMessages.EMAIL_SENT);
        } catch (Exception e) {
            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.EMAIL_NOT_SENT);
        }
    }

    //validateCode
    @Transactional(rollbackFor = {Exception.class})
    public ResponseApi<VerificationCode> validateCode(VerificationCode verificationCode) {
        try {
            verificationCode.setCode(null);
            verificationCode.setExpiresAt(null);
            verificationCode.setUpdatedAt(Instant.from(LocalDateTime.now()));
            verificationCode.setUsed(true);

            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_ACTIVATING_USER);
        } catch (Exception e) {
            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_ACTIVATING_USER);
        }
    }
}
