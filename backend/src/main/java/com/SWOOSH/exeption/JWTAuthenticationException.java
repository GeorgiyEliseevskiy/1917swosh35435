package com.SWOOSH.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JWTAuthenticationException extends AuthenticationException {

    private HttpStatus httpStatus;

    public JWTAuthenticationException(String msg) {
        super(msg);
    }

    public JWTAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
