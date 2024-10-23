package es.itx.prices.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.itx.prices.dto.PriceResponseDTO;
import es.itx.prices.exception.CustomException;
import es.itx.prices.model.Price;
import es.itx.prices.repository.PriceRepository;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public PriceResponseDTO getPrice(Long productId, Long brandId, LocalDateTime applicationDate) {

        List<Price> prices = priceRepository.findByProductIdAndBrandIdOrderByPriorityDesc(
                productId, brandId);

        prices = prices.stream().filter(p -> (p.getStartDate().isBefore(applicationDate) || p.getStartDate().isEqual(applicationDate))
        		&& (p.getEndDate().isAfter(applicationDate) || p.getEndDate().isEqual(applicationDate))).toList();
        
        if (prices.isEmpty()) {
            throw new CustomException("No price found for the given criteria.");
        }

        Price applicablePrice = prices.get(0); // Toma el de mayor prioridad
        return new PriceResponseDTO(applicablePrice);
    }
}