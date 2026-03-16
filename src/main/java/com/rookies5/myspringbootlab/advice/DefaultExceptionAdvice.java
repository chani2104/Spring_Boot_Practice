package com.rookies5.myspringbootlab.advice;

import com.rookies5.myspringbootlab.exception.BusinessException;
import com.rookies5.myspringbootlab.exception.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleBusinessException(BusinessException e) {
        ErrorObject errorObject = new ErrorObject(e.getCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObject);
    }
}