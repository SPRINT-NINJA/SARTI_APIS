package mx.edu.utez.SARTI_APIS.modules.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.cart.model.CartRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
}
