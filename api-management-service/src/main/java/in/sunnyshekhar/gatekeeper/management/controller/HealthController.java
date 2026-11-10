package in.sunnyshekhar.gatekeeper.management.controller;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sunnyshekhar.gatekeeper.management.dto.responseDto.HealthRespnse;
import in.sunnyshekhar.gatekeeper.management.service.HealthService;

@RestController 
@RequestMapping ("/health")
public class HealthController {
    public HealthService healthService;
    public HealthController(HealthService healthService){
        this.healthService= healthService;
    }
    

    @GetMapping
    public ResponseEntity<HealthRespnse> checkHealth(){
        return ResponseEntity.ok(new HealthRespnse("UP", healthService.getHealth(),LocalDateTime.now()));
    }
}
