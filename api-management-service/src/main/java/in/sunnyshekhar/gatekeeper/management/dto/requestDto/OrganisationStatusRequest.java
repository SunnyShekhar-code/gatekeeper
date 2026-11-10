package in.sunnyshekhar.gatekeeper.management.dto.requestDto;

import in.sunnyshekhar.gatekeeper.management.entity.OrganisationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter 
public class OrganisationStatusRequest {
    @NotNull 
    private OrganisationStatus status;

    
}
