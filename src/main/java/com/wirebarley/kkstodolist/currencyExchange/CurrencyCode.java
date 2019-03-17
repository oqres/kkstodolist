package com.wirebarley.kkstodolist.currencyExchange;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CurrencyCode {


    //CODE 추가시에 여기와 getcodeText에 추가 해야됨
    USDKRW, USDJPY, USDPHP;

    private static Map<String, String> codeMap = null;
    private static List<String> codeList = null;

    public static Map<String, String> getCodeMap() {
        if (codeMap == null) {
            codeMap = Stream.of(CurrencyCode.values())
                    .collect(Collectors.toMap(obj -> obj.name(), CurrencyCode::getCodeText));
        }
        return codeMap;
    }

    public static List<String> getCodeList() {
        if (codeList == null) {
            codeList = Stream.of(CurrencyCode.values())
                    .map(CurrencyCode::name)
                    .distinct().collect(Collectors.toList());
        }
        return codeList;
    }

    private static String getCodeText(CurrencyCode currencyCode) {
        switch (currencyCode) {
            case USDKRW:
                return "한국 (KRW)";
            case USDJPY:
                return "일본 (JPY)";
            case USDPHP:
                return "필리핀 (PHP)";
        }
        return null;
    }


}
