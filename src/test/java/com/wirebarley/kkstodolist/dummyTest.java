package com.wirebarley.kkstodolist;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wirebarley.kkstodolist.currencyExchange.CurrencyApiDto;
import com.wirebarley.kkstodolist.currencyExchange.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class dummyTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate restTemplate = new RestTemplate();
    final String url = "";

    @Test
    public void basicTest() throws IOException {

//        CurrencyApiRepo apiService = new CurrencyApiRepo();
//        Map<String,Object> result = apiService.getCurrencyMapByScheduled();
//        System.out.println(result);


        log.info(getCurrencyApiResult(callCurrecnyApiTest()).toString());

    }


    //repository private method 테스트
    public Map<CurrencyCode, Double> getCurrencyApiResult(Supplier<CurrencyApiDto> supplier) {
        CurrencyApiDto currencyApiDto = supplier.get();

        if (!currencyApiDto.isSuccess()) {
            log.error("error reason : {}", currencyApiDto.getError());
            throw new RuntimeException();
        }

        return currencyApiDto.getQuotes().entrySet().stream()
                .filter(entry -> currencyFilter(entry.getKey()))
                .collect(Collectors.toMap(entry -> CurrencyCode.valueOf(entry.getKey()), Map.Entry::getValue));
    }

    public boolean currencyFilter(String currecyType) {
        return CurrencyCode.getCodeList().contains(currecyType);
    }

    public Supplier<CurrencyApiDto> callCurrecnyApi() {

        Supplier<CurrencyApiDto> sup = () -> {
            return restTemplate.getForObject(url, CurrencyApiDto.class);
        };
        return sup;
    }

    public Supplier<CurrencyApiDto> callCurrecnyApiTest() {
        return () -> {
            try {
                return objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("static/json/testdata.json"), CurrencyApiDto.class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        };
    }


//    public enum TestObj{
//
//        //CODE 추가시에 여기와 getcodeTEXT에 추가 해야됨
//        USDKRW, USDJPY, USDPHP;
//
//        private static Map<String,String> codeMap;
//        private static List<String> codeList;
//
//        public static Map<String,String> getCodeMap(){
//            if(codeMap == null){
//                codeMap =  Stream.of(TestObj.values())
//                                .collect(Collectors.toMap(obj->obj.name(),TestObj::getCodeText));
//            }
//            return codeMap;
//        }
//
//        public static List<String> getCodeList(){
//            if(codeList == null){
//                codeList =  Stream.of(TestObj.values())
//                                .map(TestObj::name)
//                                .distinct().collect(Collectors.toList());
//            }
//            return codeList;
//        }
//
//
//        private static String getCodeText(TestObj teset){
//            switch (teset){
//                case USDKRW: return "한국 (KRW)";
//                case USDJPY: return "일본 (JPY)";
//                case USDPHP: return "필리핀 (PHP)";
//            }
//            return null;
//        }
//
//
//
//
//
//
//    }


}
