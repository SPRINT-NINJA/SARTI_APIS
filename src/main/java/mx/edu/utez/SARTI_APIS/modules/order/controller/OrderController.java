package mx.edu.utez.SARTI_APIS.modules.order.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.order.service.OrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
}
