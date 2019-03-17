package com.wirebarley.kkstodolist.currencyExchange;


import com.wirebarley.kkstodolist.error.CurrencyApiCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Controller
@Slf4j
public class CurrencyApiController {

    private CurrencyApiService currencyApiService;

    @Autowired
    public CurrencyApiController(CurrencyApiService currencyApiService) {
        this.currencyApiService = currencyApiService;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("codelist", CurrencyCode.getCodeMap());
        return "currency"; //view
    }

    @PostMapping("/{type}")
    @ResponseBody
    public ResponseEntity<Double> postMain(@PathVariable String type) {
        return new ResponseEntity<>(currencyApiService.getCurrencyScheduled(CurrencyCode.valueOf(type)), HttpStatus.OK);
    }

    @PostMapping("/realtime/{type}")
    @ResponseBody
    public ResponseEntity<Double> postRealtimeMain(@PathVariable String type) {
        Future<Double> result = currencyApiService.getCurrencyRealtime(CurrencyCode.valueOf(type));
        Double val = null;
        try {
            val = result.get(10, TimeUnit.SECONDS); //10초 타임아웃
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new CurrencyApiCallException();
        }
        return new ResponseEntity<>(val, HttpStatus.OK);
    }


}
