package in.sunnyshekhar.gatekeeper.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sunnyshekhar.gatekeeper.management.entity.Organisation;
import in.sunnyshekhar.gatekeeper.management.entity.OrganisationStatus;

public interface OrganisationRepository extends JpaRepository<Organisation,Long>{

    boolean existsByNameIgnoreCase(String name);
    boolean existsBySlugIgnoreCase(String slug);

    List<Organisation> findAllByStatusNot(OrganisationStatus status);

    Optional <Organisation> findByIdAndStatusNot(Long id, OrganisationStatus status);

    
} 