package com.wirebarley.kkstodolist.currencyExchange;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
public class CurrencyApiService {

    private CurrencyApiRepo currencyApiRepo;
    private ExecutorService executor = Executors.newFixedThreadPool(30);

    @Autowired
    public CurrencyApiService(CurrencyApiRepo currencyApiRepo) {
        this.currencyApiRepo = currencyApiRepo;
    }

    //api realtime 비동기 처리, 간단한 구현
    public CompletableFuture<Double> getCurrencyRealtime(CurrencyCode currencyCode) {
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() ->
                //            log.info("thread name chk!!!");
                currencyApiRepo.getCurrencyMapRealtime().get(currencyCode), executor
        );
        return future;
    }

    //scheduled time
    public Double getCurrencyScheduled(CurrencyCode currencyCode) {
        return currencyApiRepo.getCurrencyMapByScheduled().get(currencyCode);
    }


}
