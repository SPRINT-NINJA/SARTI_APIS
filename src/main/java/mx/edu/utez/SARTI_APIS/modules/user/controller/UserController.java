package mx.edu.utez.SARTI_APIS.modules.user.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.user.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
