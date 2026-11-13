package in.sunnyshekhar.gatekeeper.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sunnyshekhar.gatekeeper.management.dto.responseDto.RouteResponse;
import in.sunnyshekhar.gatekeeper.management.service.RouteService;

@RestController 
@RequestMapping ("/api/gateway/routes")
public class GatewayConfigurationController {
    private final RouteService routeService;
    public GatewayConfigurationController(RouteService routeService){
        this.routeService=routeService;
    }

    @GetMapping 
    public ResponseEntity<List<RouteResponse>> getActiveRoutes(){
        return ResponseEntity.ok(routeService.findAllActiveForGateway());
    }

    
}