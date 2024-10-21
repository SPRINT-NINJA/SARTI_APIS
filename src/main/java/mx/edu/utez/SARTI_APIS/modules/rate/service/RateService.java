package mx.edu.utez.SARTI_APIS.modules.rate.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.rate.model.RateRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RateService {
    private final RateRepository rateRepository;
}
