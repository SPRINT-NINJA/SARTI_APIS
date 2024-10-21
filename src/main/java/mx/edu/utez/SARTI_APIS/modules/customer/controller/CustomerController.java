package mx.edu.utez.SARTI_APIS.modules.customer.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.customer.service.CustomerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customer")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
}
