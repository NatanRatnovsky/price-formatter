package com.nratnovsky.priceformatter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nratnovsky.priceformatter.model.PriceRequest;
import com.nratnovsky.priceformatter.model.PriceResponse;
import com.nratnovsky.priceformatter.services.PriceFormatterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceFormatterController.class)
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class) // Enables Mockito in JUnit 5
class PriceFormatterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PriceFormatterService priceFormatterService;

    @Test
    void shouldReturnListOfCurrencies() throws Exception {
        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"USD\",\"ILS\"]"));
    }

    @Test
    void getSuccessfulStatusWithResponse() throws Exception {
        var request = getPriceRequest();

        when(priceFormatterService.formatPrice(any()))
                .thenReturn(getPriceResponse());

        mockMvc.perform(post("/api/formatprice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"value\":1,\"formattedWithCurrency\":\"1$\"}"));
    }

    private PriceResponse getPriceResponse() {
        var response = new PriceResponse();
        response.setValue(1);
        response.setFormattedWithCurrency("1$");
        response.setFormattedWithoutCurrency("1");
        response.setNetPrice(0.8403361344537815);
        response.setVatAmount(0.15966386554621848);
        return response;
    }

    private PriceRequest getPriceRequest() {
        return new PriceRequest("100", "USD");
    }
}
