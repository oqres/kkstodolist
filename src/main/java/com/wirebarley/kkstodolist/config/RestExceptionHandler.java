package com.wirebarley.kkstodolist.config;

import com.wirebarley.kkstodolist.error.CurrencyApiCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {


    @ExceptionHandler({CurrencyApiCallException.class})
    public ResponseEntity<String> exceptionForCurrencyApiCall(HttpServletRequest req, CurrencyApiCallException e) throws Exception {
        this.printErrorTrace(req, e);
        String message = "서버에 오류가 있습니다. 관리자에게 연락해주시기 바랍니다.";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void printErrorTrace(HttpServletRequest req, Exception e) {
        StringBuffer sb = new StringBuffer();
        sb.append("\trequest URI : " + req.getRequestURI() + "\n");
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\t" + element.toString()).append("\n");
        }
        log.error("\n" + sb.toString());
        log.error("exception : " + e.getMessage());
    }

}