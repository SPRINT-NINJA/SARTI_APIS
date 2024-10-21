package mx.edu.utez.SARTI_APIS.modules.cart.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.cart.service.CartService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cart")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
}
