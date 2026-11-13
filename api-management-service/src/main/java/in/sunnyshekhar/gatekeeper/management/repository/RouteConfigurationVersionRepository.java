// package in.sunnyshekhar.gatekeeper.management.repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;

// import in.sunnyshekhar.gatekeeper.management.entity.RouteConfigurationVersion;

// public interface RouteConfigurationVersionRepository extends JpaRepository<RouteConfigurationVersion, Long> {

//     List<RouteConfigurationVersion> findAllByOrganizationIdAndRouteIdOrderByVersionNumberDesc(Long organizationId,
//                                                                                               String routeId);

// }


package in.sunnyshekhar.gatekeeper.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sunnyshekhar.gatekeeper.management.entity.RouteConfigurationVersion;

public interface RouteConfigurationVersionRepository
        extends JpaRepository<RouteConfigurationVersion, Long> {

    List<RouteConfigurationVersion>
        findAllByOrganisation_IdAndRouteIdOrderByVersionNumberDesc(
            Long organisationId,
            String routeId
        );
}

