package mx.edu.utez.SARTI_APIS.modules.oder_delivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.oder_delivery.model.OrderDeliveryRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDeliveryService {
    private final OrderDeliveryRepository orderDeliveryRepository;
}
