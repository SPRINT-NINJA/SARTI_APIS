package mx.edu.utez.SARTI_APIS.modules.oder_delivery.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.oder_delivery.service.OrderDeliveryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order-delivery")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class OrderDeliveryController {
    private final OrderDeliveryService orderDeliveryService;
}
