package com.nratnovsky.priceformatter.web;

import com.nratnovsky.priceformatter.model.PriceRequest;
import com.nratnovsky.priceformatter.model.PriceResponse;
import com.nratnovsky.priceformatter.services.PriceFormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PriceFormatterController {
    private final PriceFormatterService priceFormatterService;

    private static final List<String> AVAILABLE_CURRENCIES = Arrays.asList("ILS", "USD");

    @GetMapping("/currencies")
    public ResponseEntity<List<String>> getCurrencies() {
        return ResponseEntity.ok(AVAILABLE_CURRENCIES);
    }

    @PostMapping("/formatprice")
    public ResponseEntity<PriceResponse> formatPrice(@RequestBody PriceRequest request) {
        PriceResponse response = priceFormatterService.formatPrice(request);
        return ResponseEntity.ok(response);
    }
}
