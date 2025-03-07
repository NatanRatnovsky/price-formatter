package com.nratnovsky.priceformatter.impl.service;

import com.nratnovsky.priceformatter.exception.BadRequestException;
import com.nratnovsky.priceformatter.impl.util.ValidateInputUtil;
import com.nratnovsky.priceformatter.model.PriceRequest;
import com.nratnovsky.priceformatter.model.PriceResponse;
import com.nratnovsky.priceformatter.services.PriceFormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class PriceFormatterServiceImpl extends PriceFormatterService {

    private static final Double VAT_RATE = 0.19;
    private static final Double ONE_HUNDRED = 100d;
    private static final DecimalFormat formatter = new DecimalFormat("#,##0.##");

    @Override
    public PriceResponse formatPrice(PriceRequest priceRequest) {

        ValidateInputUtil.validateInput(priceRequest);

        int cents = parseToInteger(priceRequest);
        double fullPrice = cents / ONE_HUNDRED;

        double netPrice = fullPrice / (1 + VAT_RATE);
        double vatAmount = fullPrice - netPrice;
        String formattedNumber = formatter.format(fullPrice);

        String formattedWithCurrency;

        formattedWithCurrency = addCurrencySymbol(priceRequest, formattedNumber);

        return responseBuilder(fullPrice, formattedWithCurrency, formattedNumber, netPrice, vatAmount);

    }

    private static int parseToInteger(PriceRequest priceRequest) {
        try {
            return Integer.parseInt(priceRequest.getAmount());
        } catch (NumberFormatException e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    private static String addCurrencySymbol(PriceRequest priceRequest, String formattedNumber) {
        String formattedWithCurrency;
        if ("ILS".equalsIgnoreCase(priceRequest.getCurrency())) {
            formattedWithCurrency = "₪" + formattedNumber;
        } else {
            formattedWithCurrency = formattedNumber + "$";
        }

        return formattedWithCurrency;
    }

    private static Number formatValue(Double value) {
        if(value.equals(Math.floor(value))){
            return (int)Math.floor(value);
        } else {
            return value;
        }

    }

    private static PriceResponse responseBuilder(Double fullPrice, String formattedWithCurrency, String formattedNumber, double netPrice, double vatAmount) {
        var response = new PriceResponse();
        response.setValue(formatValue(fullPrice));
        response.setFormattedWithCurrency(formattedWithCurrency);
        response.setFormattedWithoutCurrency(formattedNumber);
        response.setNetPrice(netPrice);
        response.setVatAmount(vatAmount);

        return response;
    }
}
