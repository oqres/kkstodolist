package com.wirebarley.kkstodolist;

import com.wirebarley.kkstodolist.currencyExchange.CurrencyApiController;
import com.wirebarley.kkstodolist.currencyExchange.CurrencyApiService;
import com.wirebarley.kkstodolist.currencyExchange.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.concurrent.CompletableFuture;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyApiController.class)
public class CurrencyApiControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyApiService currencyApiService;

    @Test
    public void mainTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

    }

    @Test
    public void postMainTest() throws Exception {
        Double value = 1520.02d;
        given(this.currencyApiService.getCurrencyScheduled(CurrencyCode.USDKRW)).willReturn(value);

        mvc.perform(MockMvcRequestBuilders
                .post("/{type}", "USDKRW")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(value)));
    }

    @Test
    public void realtimePostMainTest() throws Exception {
        Double value = 1520.02d;
        given(this.currencyApiService.getCurrencyRealtime(CurrencyCode.USDKRW)).willReturn(CompletableFuture.supplyAsync(() -> value));

        mvc.perform(MockMvcRequestBuilders
                .post("/realtime/{type}", "USDKRW")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(value)));

    }


}
