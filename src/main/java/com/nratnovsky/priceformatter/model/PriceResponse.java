package com.nratnovsky.priceformatter.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PriceResponse {
    private Number value;

    private String formattedWithCurrency;

    private String formattedWithoutCurrency;

    private Double netPrice;

    private Double vatAmount;
}
