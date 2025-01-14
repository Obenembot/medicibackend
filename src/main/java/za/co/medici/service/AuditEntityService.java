package za.co.medici.service;

import za.co.medici.models.AuditEntity;

import java.util.List;

public interface AuditEntityService {

    AuditEntity saveAuditEntity(AuditEntity auditEntity);

    List<AuditEntity> findAuditEntityByEntityIdAndEntityType(Long entityId, String entityType);
}
