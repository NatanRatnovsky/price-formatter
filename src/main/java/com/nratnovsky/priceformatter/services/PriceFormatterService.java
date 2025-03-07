package com.nratnovsky.priceformatter.services;

import com.nratnovsky.priceformatter.model.PriceRequest;
import com.nratnovsky.priceformatter.model.PriceResponse;


public abstract class PriceFormatterService {

    public abstract PriceResponse formatPrice(PriceRequest priceRequest);
}
