package mx.edu.utez.SARTI_APIS.modules.user.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.kernel.ResponseApi;
import mx.edu.utez.SARTI_APIS.modules.auth.service.AuthMessages;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.SARTI_APIS.modules.user.model.UserRepository;
import mx.edu.utez.SARTI_APIS.modules.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseApi<Optional<User>> findUserByEmail(String email) {
        try{
            Optional<User> user = userRepository.findByEmail(email);
            return new ResponseApi<>(user, HttpStatus.ACCEPTED, false, AuthMessages.SIGN_UP);
        } catch (Exception e) {
            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public ResponseApi<User> saveUser(User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return null;
            }
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

            return new ResponseApi<>(userRepository.save(user), HttpStatus.ACCEPTED, false, AuthMessages.SIGN_UP);
        } catch (Exception e) {
            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_SIGNING_UP);
        }
    }

    //activateUser
    @Transactional(rollbackFor = {Exception.class})
    public ResponseApi<User> activateUser(User user) {
        try {
            user.setStatus(true);
            return new ResponseApi<>(userRepository.save(user), HttpStatus.ACCEPTED, false, AuthMessages.ACTIVATED_USER);
        } catch (Exception e) {
            return new ResponseApi<>(null, HttpStatus.BAD_REQUEST, true, AuthMessages.ERROR_ACTIVATING_USER);
        }
    }

}
