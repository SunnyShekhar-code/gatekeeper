package in.sunnyshekhar.gatekeeper.gateway.event;

import in.sunnyshekhar.gatekeeper.gateway.service.GatewayConfigurationLoader;

public class ConfigurationEventConsumer {
    private final GatewayConfigurationLoader gatewayConfigurationLoader;
    public ConfigurationEventConsumer(GatewayConfigurationLoader gatewayConfigurationLoader){
        this.gatewayConfigurationLoader=gatewayConfigurationLoader;
    }
    

    public void configurationChanged(String event){
        System.out.println("Gateway configuration notification received "+ event);
        gatewayConfigurationLoader.refresh();
        System.out.println("Gateway configuration updated"+ event);
        
    }
}
