package in.sunnyshekhar.gatekeeper.management.dto.responseDto;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter 
@AllArgsConstructor 
@Setter 
public class ErrorResponse {
    private int status;
    private String message;
    private Map<String,String> errors;
    private LocalDateTime timestamp;
    
}
