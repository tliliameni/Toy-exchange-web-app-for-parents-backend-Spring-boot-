package com.example.demo.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.security.SignatureException;

 
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiErrorResponse> handleSignatureException(SignatureException sx) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.FORBIDDEN, "FORBIDDEN");
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing your request");
        ex.printStackTrace();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
