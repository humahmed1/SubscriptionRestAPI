package com.example.project;

import com.example.project.service.SubscriptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private final SubscriptionService service = new SubscriptionService();


    @Test
    @DisplayName("GET /subscriptions - Success")
    public void subGetAllTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get("/subscriptions")
                .accept(MediaType.APPLICATION_JSON);

       mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().json("[{id: 1, name:Netflix, price: 5.99, date: 9, account: Santander}, {id: 2, name:Disney+}, {id: 3, name:Spotify} ]"))
               .andReturn();
    }

    @Test
    @DisplayName("GET /subscriptions/1 - Success")
    public void subGetTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get("/subscriptions/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{id: 1, name:Netflix, price: 5.99, date: 9, account: Santander}"))
                .andReturn();
    }

    @Test
    @DisplayName("GET /subscriptions/5 - Fail")
    public void subGetNegTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get("/subscriptions/5")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("POST /subscriptions - Success")
    public void subPostTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post("/subscriptions")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"iCloud\",\"price\": 0.79,\"date\": 20, \"account\":\"Amex\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header()
                        .string("location", containsString("/subscription/4")))
                .andReturn();
    }

    @Test
    @DisplayName("PUT /subscriptions/1 - Success")
    public void subPutTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.put("/subscriptions/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Netflix\",\"price\": 8.99,\"date\": 25,\"account\": \"Santander\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{id: 1, name:Netflix, price: 8.99, date: 25, account: Santander}"))
                .andReturn();
    }

    @Test
    @DisplayName("PUT /subscriptions/5 - Fail")
    public void subPutNegTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.put("/subscriptions/5")
                .content("{\"name\":\"Netflix\",\"price\": 8.99,\"date\": 25,\"account\": \"Santander\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("DELETE /subscriptions/1 - Success")
    public void subDeleteTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.delete("/subscriptions/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().is(204))
                .andExpect(content().string(""))
                .andReturn();
    }

    @Test
    @DisplayName("DELETE /subscriptions/5 - Fail")
    public void subDeleteNegTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.delete("/subscriptions/5")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

}