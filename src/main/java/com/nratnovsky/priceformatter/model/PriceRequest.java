package com.nratnovsky.priceformatter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PriceRequest {
    private String amount;

    private String currency;
}
