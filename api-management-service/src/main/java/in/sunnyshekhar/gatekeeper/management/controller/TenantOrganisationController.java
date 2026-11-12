package in.sunnyshekhar.gatekeeper.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sunnyshekhar.gatekeeper.management.dto.requestDto.TenantOrganisationRequest;
import in.sunnyshekhar.gatekeeper.management.dto.responseDto.OrganisationResponse;
import in.sunnyshekhar.gatekeeper.management.service.OrganisationService;
import jakarta.validation.Valid;


@RestController 
@RequestMapping ("/api/tenant/organisation")
public class TenantOrganisationController {
    private OrganisationService organisationService;
    public TenantOrganisationController(OrganisationService organisationService){
        this.organisationService=organisationService;
    }

    @GetMapping 
    public ResponseEntity<OrganisationResponse> getOwnOrganisation(){
        return ResponseEntity.ok(organisationService.getOwnOrganisation());
    }

    @PutMapping 
    public ResponseEntity<OrganisationResponse> updateOwnOrganisation( @Valid  @RequestBody  TenantOrganisationRequest request){

        return ResponseEntity.ok(organisationService.updateOwnOrganisation(request));

    }
    
}
