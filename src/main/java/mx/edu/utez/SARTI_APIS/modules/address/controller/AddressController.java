package mx.edu.utez.SARTI_APIS.modules.address.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.address.service.AddressService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/address")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
}
