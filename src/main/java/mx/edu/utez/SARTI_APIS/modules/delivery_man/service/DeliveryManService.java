package mx.edu.utez.SARTI_APIS.modules.delivery_man.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.delivery_man.model.DeliveryManRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryManService {
    private final DeliveryManRepository deliveryManRepository;
}
