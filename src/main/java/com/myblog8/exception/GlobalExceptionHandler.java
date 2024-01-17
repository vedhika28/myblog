package com.myblog8.exception;

import com.myblog8.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//this becomes custom exception handler,if any exception occurs ,this class handles
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
   //handles specific exception-note
    @ExceptionHandler(ResourceNotFound.class)//Resource not found.class we created.
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFound exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    // note-global exceptions.
    @ExceptionHandler(Exception.class)//Exception.class this is builtin.
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
