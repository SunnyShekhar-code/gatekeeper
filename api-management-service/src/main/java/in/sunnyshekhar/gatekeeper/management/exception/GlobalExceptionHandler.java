package in.sunnyshekhar.gatekeeper.management.exception;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.sunnyshekhar.gatekeeper.management.dto.responseDto.ErrorResponse;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler (ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception){

        return response(exception.getStatus(),exception.getMessage(),null);
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception){
        Map<String, String> errors= new HashMap<>();
        exception.getBindingResult().getFieldErrors()
        .forEach( error -> errors.put(error.getField(),error.getDefaultMessage()));

        return response(HttpStatus.BAD_REQUEST,exception.getMessage(), errors);
    }

    @ExceptionHandler (Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception){

        return response(HttpStatus.INTERNAL_SERVER_ERROR,"unexpected server error",null);
    }







    private ResponseEntity<ErrorResponse> response(HttpStatus status,String message, Map<String,String> errors){
        ErrorResponse body= new ErrorResponse(status.value(),message,errors,LocalDateTime.now());
        return ResponseEntity.status(status.value()).body(body);
    }
    
}
