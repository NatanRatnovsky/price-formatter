package com.nratnovsky.priceformatter.impl.util;

import com.nratnovsky.priceformatter.exception.BadRequestException;
import com.nratnovsky.priceformatter.model.PriceRequest;
import lombok.extern.log4j.Log4j2;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Log4j2
public class ValidateInputUtil {

    public static void validateInput(PriceRequest priceRequest) {
        StringBuilder errorMessage = new StringBuilder();

        if (isNull(priceRequest)) {
            errorMessage.append("Price request is required. ");
            throwException(errorMessage);
        }
        if (isNull(priceRequest.getAmount()) || priceRequest.getAmount().isEmpty() || !isNumeric(priceRequest.getAmount())) {
            errorMessage.append("Amount is empty or not numeric. ");
        }
        if (isNull(priceRequest.getCurrency()) || priceRequest.getCurrency().isEmpty()) {
            errorMessage.append("Currency is required. ");
        }
        if (nonNull(priceRequest.getCurrency()) && priceRequest.getCurrency().length() != 3) {
            errorMessage.append("Currency length should be 3. ");
        }
        if (!"ILS".equals(priceRequest.getCurrency()) && !"USD".equals(priceRequest.getCurrency())) {
            errorMessage.append("Unknown currency.");
        }
        throwException(errorMessage);
    }

    private static void throwException(StringBuilder errorMessage) {
        if (!errorMessage.isEmpty()) {
            log.error(errorMessage.toString());
            throw new BadRequestException(errorMessage.toString());
        }
    }

    public static boolean isNumeric(String amount) {
        if (amount == null || amount.isEmpty()) {
            return false;
        }
        for (char c : amount.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
