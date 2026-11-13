package in.sunnyshekhar.gatekeeper.management.service;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import in.sunnyshekhar.gatekeeper.management.entity.ControlPlaneAuditRecord;
import in.sunnyshekhar.gatekeeper.management.entity.Organisation;
import in.sunnyshekhar.gatekeeper.management.repository.ControlPlaneAuditRecordRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service 
public class AuditService {

    private final ControlPlaneAuditRecordRepository auditRecordRepository;

    public AuditService(ControlPlaneAuditRecordRepository auditRecordRepository) {
        this.auditRecordRepository = auditRecordRepository;
    }

    public void record(Organisation organisation, String action,
                       String resourceType, String resourceId,
                       long startedAt, String details) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String actor = authentication == null ? "system" : authentication.getName();

        ControlPlaneAuditRecord record = new ControlPlaneAuditRecord();
        record.setOrganisation(organisation);
        record.setActor(actor);
        record.setAction(action);
        record.setResourceType(resourceType);
        record.setResourceId(resourceId);
        record.setCorrelationId(getCorrelationId());
        record.setDurationMs((System.nanoTime() - startedAt) / 1000000);
        record.setResult("SUCCESS");
        record.setDetails(details);
        record.setCreatedAt(LocalDateTime.now());

        auditRecordRepository.save(record);
    }

    private String getCorrelationId() {
        if(!(RequestContextHolder.getRequestAttributes()
                instanceof ServletRequestAttributes)) {
            return null;
        }

        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request.getHeader("X-Correlation-ID");
    }
}