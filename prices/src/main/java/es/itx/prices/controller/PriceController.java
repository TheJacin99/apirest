package es.itx.prices.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.itx.prices.dto.PriceResponseDTO;
import es.itx.prices.service.PriceService;
import es.itx.prices.util.DateUtils;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping
    public ResponseEntity<PriceResponseDTO> getPrice(
        @RequestParam("date") String dateStr,
        @RequestParam("productId") Long productId,
        @RequestParam("brandId") Long brandId) {

        LocalDateTime applicationDate = DateUtils.parseDateTime(dateStr);
        PriceResponseDTO response = priceService.getPrice(productId, brandId, applicationDate);
        
        return ResponseEntity.ok(response);
    }
}