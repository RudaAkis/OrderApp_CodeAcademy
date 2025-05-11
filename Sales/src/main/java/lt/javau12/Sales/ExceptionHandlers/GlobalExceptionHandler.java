package lt.javau12.Sales.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice //This annotation is used for
public class GlobalExceptionHandler {

    //Here we say it is an exception handler and in brackets we tell it what exceptions it will be handling
    //In the method signature we  give the actual exception object we get when exception is thrown
    @ExceptionHandler(RuntimeException.class)
    //In the response entity we are returning a Map that contains key-String and value - Object
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException exception){
        Map<String, Object> errorMap = new LinkedHashMap<>();//We use linked hash map since it keeps the order
        // of the key value pairs placed in it
        errorMap.put("error", exception.getMessage());
        errorMap.put("timeStamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}
