package com.cyngofokglobal.order_service.web.controller;


import com.cyngofokglobal.order_service.AbstractIT;
import com.cyngofokglobal.order_service.WithMockOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class GetOrdersTests  extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockOAuth2User(username = "user", role = {"ROLE_USER"})
    void shouldGetOrdersSuccessfully() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }
}
