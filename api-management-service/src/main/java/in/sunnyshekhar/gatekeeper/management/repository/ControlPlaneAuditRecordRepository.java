// package in.sunnyshekhar.gatekeeper.management.repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;

// import in.sunnyshekhar.gatekeeper.management.entity.ControlPlaneAuditRecord;

// public interface ControlPlaneAuditRecordRepository extends JpaRepository<ControlPlaneAuditRecord, Long> {

//     List<ControlPlaneAuditRecord> findAllByOrganizationIdOrderByCreatedAtDesc(Long organisationId);
// }


package in.sunnyshekhar.gatekeeper.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sunnyshekhar.gatekeeper.management.entity.ControlPlaneAuditRecord;

public interface ControlPlaneAuditRecordRepository
        extends JpaRepository<ControlPlaneAuditRecord, Long> {

    List<ControlPlaneAuditRecord>
        findAllByOrganisation_IdOrderByCreatedAtDesc(Long organisationId);
}

