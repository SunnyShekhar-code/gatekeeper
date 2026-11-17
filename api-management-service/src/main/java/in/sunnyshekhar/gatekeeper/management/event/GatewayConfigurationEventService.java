package in.sunnyshekhar.gatekeeper.management.event;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service 
public class GatewayConfigurationEventService {

    private final ApplicationEventPublisher eventPublisher;
    public GatewayConfigurationEventService(ApplicationEventPublisher eventPublisher){
        this.eventPublisher=eventPublisher;
    }

    public void publish(String eventType,String routeId,Long organisationId, Long configurationVersion){
        eventPublisher.publishEvent(
            new GatewayConfigurationChangeEvent(UUID.randomUUID().toString(),eventType,
            "GATEWAY_CONFIGURATION_CHANGED",routeId,organisationId,configurationVersion,LocalDateTime.now())
            );

            
    }
    
}
