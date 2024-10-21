package mx.edu.utez.SARTI_APIS.modules.seller.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.SARTI_APIS.modules.seller.model.SellerRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
}
