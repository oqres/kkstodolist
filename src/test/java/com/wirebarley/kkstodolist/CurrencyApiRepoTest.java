package com.wirebarley.kkstodolist;

import com.wirebarley.kkstodolist.currencyExchange.CurrencyApiRepo;
import com.wirebarley.kkstodolist.currencyExchange.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CurrencyApiRepoTest {

    final String url = "http://dummy";
    final String key = "foo";

    @Test
    public void mainTest() throws Exception {

        CurrencyApiRepo apiRepo = new CurrencyApiRepo(
                new RestTemplate()
                , url
                , key
        );
        assertThat(apiRepo.getCurrencyMapRealtime()).isNotEmpty();
        assertThat(apiRepo.getCurrencyMapByScheduled()).isNotEmpty();

    }


}
