package com.nratnovsky.priceformatter.impl.service;

import com.nratnovsky.priceformatter.exception.BadRequestException;
import com.nratnovsky.priceformatter.model.PriceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceFormatterServiceImplTest {

    private PriceFormatterServiceImpl service;

    @BeforeEach
    void setUp() {
      service = new PriceFormatterServiceImpl();
    }


    @Test
    void testFormatPriceForILS() {
        var request = getPriceRequestWithIlsCurrency();
        var response = service.formatPrice(request);

        assertInstanceOf(Double.class, response.getValue());
        assertEquals(20.02, response.getValue());
        assertEquals("₪20.02", response.getFormattedWithCurrency());
        assertEquals("20.02", response.getFormattedWithoutCurrency());
        assertEquals(16.823529411764707, response.getNetPrice());
        assertEquals(3.196470588235293, response.getVatAmount());
    }

    @Test
    void testFormatPriceForUSD_IntegerValue() {
        var request = getPriceRequestWithUsdCurrency();
        var response = service.formatPrice(request);

        assertInstanceOf(Integer.class, response.getValue());
        assertEquals(100000, response.getValue());
        assertEquals("100,000$", response.getFormattedWithCurrency());
        assertEquals("100,000", response.getFormattedWithoutCurrency());
        assertEquals(84033.61344537816, response.getNetPrice());
        assertEquals(15966.386554621844, response.getVatAmount());
    }

    @Test
    void testFormatPriceWithInvalidAmount() {
        var request = getPriceRequestWithIlsCurrency();
        request.setAmount("abc");

        assertThrows(BadRequestException.class, () -> service.formatPrice(request));
    }

    private PriceRequest getPriceRequestWithUsdCurrency() {
        return new PriceRequest("10000000", "USD");
    }

    private PriceRequest getPriceRequestWithIlsCurrency() {
        return new PriceRequest("2002", "ILS");
    }
}
