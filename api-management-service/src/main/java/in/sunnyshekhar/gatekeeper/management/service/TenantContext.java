package in.sunnyshekhar.gatekeeper.management.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import in.sunnyshekhar.gatekeeper.management.entity.Organisation;
import in.sunnyshekhar.gatekeeper.management.entity.OrganisationStatus;
import in.sunnyshekhar.gatekeeper.management.exception.ApiException;
import in.sunnyshekhar.gatekeeper.management.repository.OrganisationRepository;


@Component 
public class TenantContext {

    private final OrganisationRepository organisationRepository;
    public TenantContext(OrganisationRepository organisationRepository){
        this.organisationRepository=organisationRepository;
    }

    public Organisation getOrganisation(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof JwtAuthenticationToken)) throw new ApiException(HttpStatus.FORBIDDEN, "Tenant identity is missing");

        Long organisationId= ((JwtAuthenticationToken)authentication).getToken().getClaim("organisationId");

        if(organisationId==null) throw new ApiException(HttpStatus.FORBIDDEN, "Tenant identity is missing");

        Organisation organisation= organisationRepository.findById(organisationId)
                                   .orElseThrow(()-> new ApiException(HttpStatus.FORBIDDEN, "Organisation does not exist"));

        if(organisation.getStatus() != OrganisationStatus.ACTIVE) throw new ApiException(HttpStatus.FORBIDDEN, "The organisation is not active");

        return organisation;

    }
    
}
