package mx.edu.utez.SARTI_APIS.modules.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.order.model.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
}
