package in.sunnyshekhar.gatekeeper.management.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.sunnyshekhar.gatekeeper.management.dto.requestDto.OrganisationRequest;
import in.sunnyshekhar.gatekeeper.management.dto.requestDto.OrganisationStatusRequest;
import in.sunnyshekhar.gatekeeper.management.dto.requestDto.TenantAdminRequest;
import in.sunnyshekhar.gatekeeper.management.dto.responseDto.OrganisationResponse;
import in.sunnyshekhar.gatekeeper.management.dto.responseDto.TenantAdminResponse;
import in.sunnyshekhar.gatekeeper.management.entity.ManagementUser;
import in.sunnyshekhar.gatekeeper.management.entity.Organisation;
import in.sunnyshekhar.gatekeeper.management.entity.OrganisationStatus;
import in.sunnyshekhar.gatekeeper.management.entity.Role;
import in.sunnyshekhar.gatekeeper.management.exception.ApiException;
import in.sunnyshekhar.gatekeeper.management.repository.ManagementUserRepository;
import in.sunnyshekhar.gatekeeper.management.repository.OrganisationRepository;
import in.sunnyshekhar.gatekeeper.management.repository.RoleRepository;

@Service 
public class OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final ManagementUserRepository managementUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public OrganisationService(OrganisationRepository organisationRepository,ManagementUserRepository managementUserRepository, 
                RoleRepository roleRepository,PasswordEncoder passwordEncoder){
                            this.organisationRepository=organisationRepository;
                            this.managementUserRepository=managementUserRepository;
                            this.roleRepository=roleRepository;
                            this.passwordEncoder=passwordEncoder;
    }


    @Transactional 
    public OrganisationResponse create(OrganisationRequest request){
        long startedAt=System.nanoTime();

        checkUnique(request.getName(),request.getSlug(),null);

        Organisation organisation= new Organisation();
        organisation.setName(request.getName());
        organisation.setSlug(request.getSlug());
        organisation.setStatus(OrganisationStatus.PENDING);
        organisation.setCreatedAt(LocalDateTime.now());
        organisation.setUpdatedAt(LocalDateTime.now());


        organisationRepository.save(organisation);

        return OrganisationResponse.from(organisation);

    }

    @Transactional(readOnly = true)
    public List<OrganisationResponse> findAll(){
        List<OrganisationResponse>  response= new ArrayList<>();

        for(Organisation organisation : organisationRepository.findAllByStatusNot(OrganisationStatus.DELETED)){
            response.add(OrganisationResponse.from(organisation));
        };

        return response;
    }

    @Transactional (readOnly = true)
    public OrganisationResponse findById(Long id){
        return OrganisationResponse.from(getOrganisation(id));
    }

    @Transactional
    public OrganisationResponse update(Long id, OrganisationRequest request) {
        long startedAt = System.nanoTime();
        Organisation organisation = getOrganisation(id);

        checkUnique(request.getName(), request.getSlug(), id);

        organisation.setName(request.getName());
        organisation.setSlug(request.getSlug());
        organisation.setUpdatedAt(LocalDateTime.now());

        return OrganisationResponse.from(organisation);
    }

    @Transactional
    public OrganisationResponse changeStatus(Long id, OrganisationStatusRequest request) {

        long startedAt = System.nanoTime();
        Organisation organization = getOrganisation(id);

        if(request.getStatus() == OrganisationStatus.DELETED) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Use DELETE to soft-delete an organization");
        }

        organization.setStatus(request.getStatus());
        organization.setUpdatedAt(LocalDateTime.now());

        return OrganisationResponse.from(organization);
    }

    @Transactional
    public void delete(Long id) {
        long startedAt = System.nanoTime();

        Organisation organisation = getOrganisation(id);
        organisation.setStatus(OrganisationStatus.DELETED);
        organisation.setDeletedAt(LocalDateTime.now());
        organisation.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public TenantAdminResponse createTenantAdmin(Long organisationId, TenantAdminRequest request) {
        long startedAt = System.nanoTime();
        Organisation organisation = getOrganisation(organisationId);

        if(managementUserRepository.findByOrganisationId(organisationId).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "This organization already has a tenant administrator");
        }

        if(managementUserRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already exists");
        }

        Role role = roleRepository.findByName("TENANT_ADMIN")
                .orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "TENANT_ADMIN role is missing"));

        ManagementUser user = new ManagementUser();
        user.setOrganisation(organisation);
        user.setRole(role);
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(request.isEnabled());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        managementUserRepository.save(user);

        return TenantAdminResponse.from(user);
    }

    @Transactional(readOnly = true)
    public TenantAdminResponse getTenantAdmin(Long organizationId) {
        return TenantAdminResponse.from(getTenantAdminEntity(organizationId));
    }


    @Transactional
    public TenantAdminResponse updateTenantAdmin(Long organizationId, TenantAdminRequest request) {
        long startedAt = System.nanoTime();
        ManagementUser user = getTenantAdminEntity(organizationId);

        if(!user.getUsername().equals(request.getUsername())
                && managementUserRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already exists");
        }

        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(request.isEnabled());
        user.setUpdatedAt(LocalDateTime.now());

        return TenantAdminResponse.from(user);
    }

     @Transactional
    public void deleteTenantAdmin(Long organizationId) {
        long startedAt = System.nanoTime();

        ManagementUser user = getTenantAdminEntity(organizationId);

        managementUserRepository.delete(user);
    }
    

    private ManagementUser getTenantAdminEntity(Long organisationId) {
        getOrganisation(organisationId);
        return managementUserRepository.findByOrganisationId(organisationId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Tenant administrator not found"));
    }


    private Organisation getOrganisation(Long id) {
        return organisationRepository.findByIdAndStatusNot(id, OrganisationStatus.DELETED)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Organization not found"));
    }

    private void checkUnique(String name, String slug, Long currentId) {
        if(organisationRepository.existsByNameIgnoreCase(name)) {
            Organisation current = currentId == null ?
                    null : organisationRepository.findById(currentId).orElse(null);

            if(current == null || !current.getName().equalsIgnoreCase(name)) {
                throw new ApiException(HttpStatus.CONFLICT, "Organization name already exists");
            }
        }
      }
    
}
