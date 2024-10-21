package mx.edu.utez.SARTI_APIS.modules.rate.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.rate.service.RateService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rate")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class RateController {
    private final RateService rateService;
}
