package com.nratnovsky.priceformatter.impl.util;

import com.nratnovsky.priceformatter.exception.BadRequestException;
import com.nratnovsky.priceformatter.model.PriceRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateInputUtilTest {

    @Test
    void testValidInputILS() {
        var request = getValidPriceRequest();
        assertDoesNotThrow(() -> ValidateInputUtil.validateInput(request));
    }

    @Test
    void testNullPriceRequest() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            ValidateInputUtil.validateInput(null);
        });
        assertTrue(exception.getMessage().contains("Price request is required."));
    }

    @Test
    void testNullAmount() {
        var request = getValidPriceRequest();
        request.setAmount(null);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            ValidateInputUtil.validateInput(request);
        });
        assertTrue(exception.getMessage().contains("Amount is empty or not numeric."));
    }

    @Test
    void testEmptyAmount() {
        var request = getValidPriceRequest();
        request.setAmount("");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            ValidateInputUtil.validateInput(request);
        });
        assertTrue(exception.getMessage().contains("Amount is empty or not numeric."));
    }

    @Test
    void testNonNumericAmount() {
        var request = getValidPriceRequest();
        request.setAmount("12A3");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            ValidateInputUtil.validateInput(request);
        });
        assertTrue(exception.getMessage().contains("Amount is empty or not numeric."));
    }

    @Test
    void testNullCurrency() {
        var request = getValidPriceRequest();
        request.setCurrency(null);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            ValidateInputUtil.validateInput(request);
        });
        assertTrue(exception.getMessage().contains("Currency is required."));
    }

    @Test
    void testEmptyCurrency() {
        var request = getValidPriceRequest();
        request.setCurrency("");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            ValidateInputUtil.validateInput(request);
        });
        String message = exception.getMessage();
        assertTrue(message.contains("Currency is required."));
        assertTrue(message.contains("Currency length should be 3."));
        assertTrue(message.contains("Unknown currency."));
    }

    @Test
    void testInvalidCurrencyLength() {
        var request = getValidPriceRequest();
        request.setCurrency("US");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            ValidateInputUtil.validateInput(request);
        });
        String message = exception.getMessage();
        assertTrue(message.contains("Currency length should be 3."));
        assertTrue(message.contains("Unknown currency."));
    }

    @Test
    void testUnknownCurrency() {
        var request = getValidPriceRequest();
        request.setCurrency("EUR");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            ValidateInputUtil.validateInput(request);
        });
        assertTrue(exception.getMessage().contains("Unknown currency."));
    }

    @Test
    void testIsNumericValid() {
        assertTrue(ValidateInputUtil.isNumeric("123456"));
    }

    @Test
    void testIsNumericNonNumeric() {
        assertFalse(ValidateInputUtil.isNumeric("12a34"));
    }

    @Test
    void testIsNumericNull() {
        assertFalse(ValidateInputUtil.isNumeric(null));
    }

    @Test
    void testIsNumericEmpty() {
        assertFalse(ValidateInputUtil.isNumeric(""));
    }

    private PriceRequest getValidPriceRequest() {
        return new PriceRequest("1000", "ILS");
    }
}
