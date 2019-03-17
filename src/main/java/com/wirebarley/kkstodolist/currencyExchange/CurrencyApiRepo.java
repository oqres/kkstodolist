package com.wirebarley.kkstodolist.currencyExchange;


import com.wirebarley.kkstodolist.error.CurrencyApiCallException;
import com.wirebarley.kkstodolist.utils.CustomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class CurrencyApiRepo {


    private RestTemplate restTemplate;
    private String url, key;

    @Autowired
    public CurrencyApiRepo(RestTemplate restTemplate
            , @Value("${currencyapi.url}") String url
            , @Value("${currencyapi.key}") String key
    ) {
        this.url = url;
        this.key = key;
        this.restTemplate = restTemplate;
    }

    private Map<CurrencyCode, Double> currencyMapByScheduled; //임시 저장

    public Map<CurrencyCode, Double> getCurrencyMapByScheduled() { //최초 호출에 null point 안뜨도록 realtime에서 한번 호출 하도록 함
        if (currencyMapByScheduled == null) {
            currencyMapByScheduled = getCurrencyMapRealtime();
        }
        return currencyMapByScheduled;
    }

    //properties로 뺄 수 있는지 체크, 1시간마다
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void callApiForScheduling() {
        log.info("scheduled!!!!!!!!!!!! now! : {}", LocalDateTime.now());
        this.currencyMapByScheduled = getCurrencyApiResult(callCurrecnyApi());
    }

    //realtime으로 호출 하는 경우
    public Map<CurrencyCode, Double> getCurrencyMapRealtime() {
        return getCurrencyApiResult(callCurrecnyApi());
    }

    //api 호출 부분
    private Supplier<CurrencyApiDto> callCurrecnyApi() {
        //프로퍼티로 호출 하는 형식으로 바꿔야됨
        String reqUrl = url + key;
        Supplier<CurrencyApiDto> sup = () -> {
            return restTemplate.getForObject(reqUrl, CurrencyApiDto.class);
        };
        return sup;
    }

    //필터링 해서 CurrencyCode에 매핑되어 있는 부분만 정리해서 가지고 있도록 한다
    private Map<CurrencyCode, Double> getCurrencyApiResult(Supplier<CurrencyApiDto> supplier) {
        CurrencyApiDto currencyApiDto = supplier.get(); //callCurrencyApi

        //exception 던지기, 실패시에 로그 , false 인 경우 코드값 돌려줌
        if (!currencyApiDto.isSuccess()) {
            log.error("error reason : {}", currencyApiDto.getError());
            throw new CurrencyApiCallException();
        }

        return currencyApiDto.getQuotes().entrySet().stream()
                .filter(entry -> currencyFilter(entry.getKey()))
                .collect(Collectors.toMap(entry -> CurrencyCode.valueOf(entry.getKey()), Map.Entry::getValue));
    }

    private boolean currencyFilter(String currecyType) {
        return CurrencyCode.getCodeList().contains(currecyType);
    }


}
