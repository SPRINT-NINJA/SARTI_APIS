package mx.edu.utez.SARTI_APIS.modules.delivery_man.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.delivery_man.service.DeliveryManService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/delivery-man")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class DeliveryManController {
    private final DeliveryManService deliveryManService;
}
