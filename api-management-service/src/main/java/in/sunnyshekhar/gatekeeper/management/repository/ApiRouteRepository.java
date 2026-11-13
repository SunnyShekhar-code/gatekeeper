
package in.sunnyshekhar.gatekeeper.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sunnyshekhar.gatekeeper.management.entity.ApiRoute;
import in.sunnyshekhar.gatekeeper.management.entity.OrganisationStatus;

public interface ApiRouteRepository extends JpaRepository<ApiRoute, Long> {

    List<ApiRoute> findAllByOrganisation_Id(Long organizationId);

    List<ApiRoute> findAllByActiveTrueAndOrganisation_Status(
            OrganisationStatus status);

  
    Optional<ApiRoute> findByIdAndOrganisation_Id(
            Long id, Long organizationId);

    boolean existsByOrganisation_IdAndRouteId(
            Long organizationId, String routeId);
}
