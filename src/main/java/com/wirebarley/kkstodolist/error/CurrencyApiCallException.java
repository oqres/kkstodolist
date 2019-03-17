package com.wirebarley.kkstodolist.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CurrencyApiCallException extends RuntimeException {


    public CurrencyApiCallException() {
        super();
    }

    public CurrencyApiCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyApiCallException(String message) {
        super(message);
    }

    public CurrencyApiCallException(Throwable cause) {
        super(cause);
    }

}
