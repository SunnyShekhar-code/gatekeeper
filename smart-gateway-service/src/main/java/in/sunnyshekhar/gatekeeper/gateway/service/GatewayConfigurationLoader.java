package in.sunnyshekhar.gatekeeper.gateway.service;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.sunnyshekhar.gatekeeper.gateway.client.ManagementApiClient;
import in.sunnyshekhar.gatekeeper.gateway.dto.responseDto.GatewayRoute;


@Component 
public class GatewayConfigurationLoader implements ApplicationRunner{

    private final ManagementApiClient managementApiClient;

    public GatewayConfigurationLoader(ManagementApiClient managementApiClient){
        this.managementApiClient= managementApiClient;
    }

    @Override 
    public void run(ApplicationArguments args){
        List<GatewayRoute> routes= managementApiClient.fetchActivRoutes();

        for(GatewayRoute route: routes){
            System.out.println("Route : "+route.getTargetPathPrefix());
        }
    }
    
}
