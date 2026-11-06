package in.sunnyshekhar.gatekeeper.analytics.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sunnyshekhar.gatekeeper.analytics.dto.HealthRespnse;




@RestController
@RequestMapping ("/health")
public class HealthController {
    private String serviceName;
    private String description;

    public HealthController(@Value ("${info.app.servicename}") String serviceName,
     @Value("${info.app.description}") String description){
        this.serviceName=serviceName;
        this.description=description;
    }
    

    @GetMapping
    public ResponseEntity<HealthRespnse> healthCheck(){

        return ResponseEntity.ok(new HealthRespnse(serviceName, description,"Up"));
    }

    
}
