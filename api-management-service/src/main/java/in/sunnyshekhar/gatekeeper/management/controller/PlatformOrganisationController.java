package in.sunnyshekhar.gatekeeper.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sunnyshekhar.gatekeeper.management.dto.requestDto.OrganisationRequest;
import in.sunnyshekhar.gatekeeper.management.dto.requestDto.OrganisationStatusRequest;
import in.sunnyshekhar.gatekeeper.management.dto.requestDto.TenantAdminRequest;
import in.sunnyshekhar.gatekeeper.management.dto.responseDto.OrganisationResponse;
import in.sunnyshekhar.gatekeeper.management.dto.responseDto.TenantAdminResponse;
import in.sunnyshekhar.gatekeeper.management.service.OrganisationService;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/platform/organisations")
public class PlatformOrganisationController {
    private OrganisationService organisationService;
    public PlatformOrganisationController(OrganisationService organisationService){
        this.organisationService=organisationService;
    }


    // 1 create organisation and tenent
    @PostMapping ("/create")
    public ResponseEntity<OrganisationResponse> create(@Valid  @RequestBody OrganisationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(organisationService.create(request));
    }
    // 2 read all org
    @GetMapping ("/get-all")
    public ResponseEntity<List<OrganisationResponse>> findAll(){
        return ResponseEntity.ok(organisationService.findAll());
    }
    // 3 read one org

    @GetMapping ("/{id}")
    public ResponseEntity<OrganisationResponse> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(organisationService.findById(id));
    }
    // 4 update
    @PutMapping ("/{id}")
    public ResponseEntity<OrganisationResponse> update(@PathVariable Long id, @Valid @RequestBody OrganisationRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(organisationService.update(id,request));
    }
    // 5 patch
    @PatchMapping ("/{id}/status")
    public ResponseEntity<OrganisationResponse> changeStatus(@PathVariable Long id, @Valid @RequestBody OrganisationStatusRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(organisationService.changeStatus(id,request));
    }
    // 6 soft delete

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        organisationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    

    // CRUD --> Tenant Admins
    @PostMapping("/{organisationId}/tenant-admin")
    public ResponseEntity<TenantAdminResponse> createTenantAdmin(@PathVariable Long organisationId,
                                                                 @Valid @RequestBody TenantAdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(organisationService.createTenantAdmin(organisationId, request));
    }

    @GetMapping("/{organisationId}/tenant-admin")
    public ResponseEntity<TenantAdminResponse> getTenantAdmin(@PathVariable Long organisationId) {
        return ResponseEntity.ok(organisationService.getTenantAdmin(organisationId));
    }

    @PutMapping("/{organisationId}/tenant-admin")
    public ResponseEntity<TenantAdminResponse> updateTenantAdmin(@PathVariable Long organisationId,
                                                                 @Valid @RequestBody TenantAdminRequest request) {
        return ResponseEntity.ok(organisationService.updateTenantAdmin(organisationId, request));
    }

    @DeleteMapping("/{organisationId}/tenant-admin")
    public ResponseEntity<Void> deleteTenantAdmin(@PathVariable Long organisationId) {
        organisationService.deleteTenantAdmin(organisationId);
        return ResponseEntity.noContent().build();
    }


    
}
