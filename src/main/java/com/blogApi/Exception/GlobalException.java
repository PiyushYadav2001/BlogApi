package com.blogApi.Exception;

import com.blogApi.playload.ApiResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
     @ExceptionHandler(ResourceNotFoundException.class)
      public ResponseEntity<ApiResponce> resourceNotFoundExceptionHandler(ResourceNotFoundException rx) {
         String messages=rx.getMessage();
         ApiResponce apiResponce=new ApiResponce(messages,false);
         return  new ResponseEntity<ApiResponce>(apiResponce, HttpStatus.NOT_FOUND);

     }
     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<Map<String,String>> hamdleMethodArgumentNotValidException(MethodArgumentNotValidException MX){
       Map<String,String> map=new HashMap<>();
       MX.getBindingResult().getAllErrors().forEach(objectError -> {
           String field = ((FieldError) objectError).getField();
           String defaultMessage = objectError.getDefaultMessage();
           map.put(field,defaultMessage);
       });
       return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
     }
}
