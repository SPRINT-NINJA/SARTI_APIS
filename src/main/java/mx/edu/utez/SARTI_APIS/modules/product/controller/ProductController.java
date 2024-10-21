package mx.edu.utez.SARTI_APIS.modules.product.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.product.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
}
