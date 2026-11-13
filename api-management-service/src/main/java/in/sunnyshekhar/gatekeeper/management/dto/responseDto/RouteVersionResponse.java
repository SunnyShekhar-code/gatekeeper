package in.sunnyshekhar.gatekeeper.management.dto.responseDto;

import java.time.LocalDateTime;

import in.sunnyshekhar.gatekeeper.management.entity.RouteConfigurationVersion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter 
@AllArgsConstructor 
public class RouteVersionResponse {

    private Long id;
    private String routeId;
    private Long versionNumber;
    private String changeType;
    private String configurationSnapShot;
    private LocalDateTime createdAt;

    public static RouteVersionResponse from(RouteConfigurationVersion version){
        return new RouteVersionResponse(version.getId(),version.getRouteId(),version.getVersionNumber(),
        version.getChangeType(),version.getConfigurationSnapshot(),LocalDateTime.now());

    }

    
}
