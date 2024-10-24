package mx.edu.utez.SARTI_APIS.modules.auth.controller;

import jakarta.validation.Valid;
import mx.edu.utez.SARTI_APIS.kernel.ResponseApi;
import mx.edu.utez.SARTI_APIS.modules.auth.controller.dto.LoginDto;
import mx.edu.utez.SARTI_APIS.modules.auth.controller.dto.RegisterDto;
import mx.edu.utez.SARTI_APIS.modules.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseApi<String>> login(@Valid @RequestBody LoginDto login ) {
        ResponseApi<String> response = authService.signIn(login.getEmail(), login.getPassword());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseApi<Object>> register(@Valid @RequestBody RegisterDto registerDto) {
        ResponseApi<Object> response = authService.signUp(registerDto);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
