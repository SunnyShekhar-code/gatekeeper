package in.sunnyshekhar.gatekeeper.management.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import in.sunnyshekhar.gatekeeper.management.entity.ApiRoute;
import in.sunnyshekhar.gatekeeper.management.entity.OrganisationStatus;

public interface ApiRouteRepository extends JpaRepository<ApiRoute,Long>{
    List<ApiRoute> findAllByOrganizationId(Long organizationId);

    List<ApiRoute> findAllByActiveTrueAndOrganizationStatus(OrganisationStatus status);

    Optional<ApiRoute> findByIdAndOrganizationId(Long id, Long organizationId);

    boolean existsByOrganizationIdAndRouteId(Long organizationId, String routeId);
    
}
