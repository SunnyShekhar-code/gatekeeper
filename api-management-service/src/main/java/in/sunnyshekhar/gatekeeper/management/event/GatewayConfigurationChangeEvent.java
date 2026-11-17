package in.sunnyshekhar.gatekeeper.management.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter 
@AllArgsConstructor 
public class GatewayConfigurationChangeEvent {
    private String eventId;
    private String eventType;
    private String changeType;
    private String routeId;
    private Long organisationId;
    private Long configurationVersion;
    private LocalDateTime occurredAt;
}
