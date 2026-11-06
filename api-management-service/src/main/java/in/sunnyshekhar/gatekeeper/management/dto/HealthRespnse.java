package in.sunnyshekhar.gatekeeper.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@AllArgsConstructor
@Getter 
@Setter 
public class HealthRespnse {
    private String serviceName;
    private String serviceDescription;
    private String status;
    
    
}
