package in.sunnyshekhar.gatekeeper.management.dto.responseDto;

import java.time.LocalDateTime;

import in.sunnyshekhar.gatekeeper.management.entity.Organisation;
import in.sunnyshekhar.gatekeeper.management.entity.OrganisationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor 
public class OrganisationResponse {

    private Long id;
    private String name;
    private String slug;
    private OrganisationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static OrganisationResponse from(Organisation organisation){
        return new OrganisationResponse(
            organisation.getId(),
            organisation.getName(),
            organisation.getSlug(),
            organisation.getStatus(),
            organisation.getCreatedAt(),
            organisation.getUpdatedAt());
    }
    
}
