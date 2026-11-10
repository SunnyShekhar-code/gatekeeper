package in.sunnyshekhar.gatekeeper.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sunnyshekhar.gatekeeper.management.entity.ManagementUser;

@Repository 
public interface ManagementUserRepository extends JpaRepository<ManagementUser,Long>{
    
    Optional<ManagementUser> findByUsername(String username);
    Optional<ManagementUser> findByOrganisationId(Long organisationId);

    boolean existsByUsername(String username);
}
