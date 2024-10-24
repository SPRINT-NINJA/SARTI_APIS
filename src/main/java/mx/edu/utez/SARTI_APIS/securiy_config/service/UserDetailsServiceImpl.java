package mx.edu.utez.SARTI_APIS.securiy_config.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.edu.utez.SARTI_APIS.modules.user.model.User;
import mx.edu.utez.SARTI_APIS.modules.user.service.UserService;
import mx.edu.utez.SARTI_APIS.securiy_config.model.AuthUser;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.userService.findUserByEmail(email).getData();
        if (user.isPresent()) {
            return AuthUser.build(user.get());
        }
        throw new UsernameNotFoundException("User not found");
    }
}
