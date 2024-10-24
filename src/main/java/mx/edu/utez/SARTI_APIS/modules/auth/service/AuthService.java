package mx.edu.utez.SARTI_APIS.modules.auth.service;

import mx.edu.utez.SARTI_APIS.kernel.ResponseApi;
import mx.edu.utez.SARTI_APIS.modules.user.model.Roles;
import mx.edu.utez.SARTI_APIS.modules.verification_code.model.VerificationCode;
import mx.edu.utez.SARTI_APIS.modules.verification_code.service.VerificationCodeService;
import mx.edu.utez.SARTI_APIS.securiy_config.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.SARTI_APIS.modules.user.model.User;
import mx.edu.utez.SARTI_APIS.modules.auth.controller.dto.RegisterDto;

import mx.edu.utez.SARTI_APIS.modules.user.service.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final VerificationCodeService verificationCodeService;

    public AuthService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserService userService, VerificationCodeService verificationCodeService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.verificationCodeService = verificationCodeService;
    }

    @Transactional
    public ResponseApi<String> signIn(String email, String password) {
        try {
            Optional<User> foundUser = userService.findUserByEmail(email).getData();
            if (foundUser.isEmpty())
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.NOT_FOUND);
            User user = foundUser.get();
            if (!user.getStatus())
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.NOT_FOUND);
            if (!user.getBlocked())
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.NOT_FOUND);

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtProvider.generatedToken(auth);

            //update lastAccess y token en la base de data
            LocalDateTime lastAccess = LocalDateTime.now();
            user.setLastAccess(Instant.from(lastAccess));
            user.setToken(token);

            //userService.updateLoginInfo(user);
            return new ResponseApi<>(token, HttpStatus.OK, false, AuthMessages.SIGN_IN);
        } catch (Exception e) {
            String message = AuthMessages.PASSWORD_USER_NOT_MATCH;
            if (e instanceof DisabledException)
                message = AuthMessages.DISABLED;
            return new ResponseApi<>(null, HttpStatus.FORBIDDEN, true, message);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public ResponseApi<Object> signUp(RegisterDto registerDto) {
        try {

            if (!registerDto.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.EMAIL_BAD_FORMAT);
            }

            if (!registerDto.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")){
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.PASSWORD_BAD_FORMAT);
            }

            Optional<User> foundUser = userService.findUserByEmail(registerDto.getEmail()).getData();
            if (foundUser.isPresent())
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ALREADY_EXISTS);

            User user = new User(registerDto.getEmail(), registerDto.getPassword());

            switch (registerDto.getRole()) {
                case 1:
                    user.setRole(Roles.COMPRADOR);
                    break;
                case 2:
                    user.setRole(Roles.REPARTIDOR);
                    break;
                case 3:
                    user.setRole(Roles.EMPRENDEDOR);
                    break;
                default:
                    return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ROLE_ILLEGAL);
            }

            User userSaved = userService.saveUser(user).getData();
            if (userSaved == null) {
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_USER_NOT_SAVED);
            }

            ResponseApi<String> response = verificationCodeService.generateCode(userSaved.getEmail(), userSaved);
            if (response.isError()) {
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_SENDING_EMAIL);
            }

            return new  ResponseApi<>(null, HttpStatus.OK, false, AuthMessages.SIGN_UP);
        } catch (Exception e) {
            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_SIGNING_UP);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public ResponseApi<Object> confirmAccount(String code, String email) {
        try {
            Optional<User> foundUser = userService.findUserByEmail(email).getData();
            if (foundUser.isEmpty()) {
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.NOT_FOUND);
            }
            User user = foundUser.get();

            Optional<VerificationCode> verificationCode = verificationCodeService.findVerificationCodeByEmailAndCode(user, code).getData();
            if (verificationCode.isEmpty()) {
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.VERIFICATION_CODE_NOT_FOUND);
            }

            if (!(verificationCode.get().getCode().equals(code) && verificationCode.get().getExpiresAt().isAfter(Instant.from(LocalDateTime.now())))) {
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_CONFIRMING);
            }
            ResponseApi<VerificationCode> savedVerificationCode = verificationCodeService.validateCode(verificationCode.get());
            ResponseApi<User> savedUser = userService.activateUser(user);

            if (savedUser.isError() || savedVerificationCode.isError()) {
                return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_CONFIRMING);
            }

            return new ResponseApi<>(null, HttpStatus.OK, false, AuthMessages.CONFIRMED);

        } catch (Exception e) {
            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_CONFIRMING);
        }
    }
}
