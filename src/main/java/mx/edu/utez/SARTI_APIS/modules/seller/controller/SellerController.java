package mx.edu.utez.SARTI_APIS.modules.seller.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.seller.service.SellerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/seller")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
}
