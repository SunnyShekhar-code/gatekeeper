package in.sunnyshekhar.gatekeeper.management.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class LoginResponse {
    private String token;
    private String tokenType;
    private Long expiresInSeconds;
    private String role;
    private Long organisationId;
    
}
