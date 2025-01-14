package za.co.medici.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.medici.models.AuditEntity;
import za.co.medici.repositories.AuditEntityRepository;
import za.co.medici.service.AuditEntityService;
import za.co.medici.uitls.Constants;

import java.util.List;

@Service
public class AuditEntityServiceImpl implements AuditEntityService {

    private final Logger logger = LoggerFactory.getLogger(AuditEntityServiceImpl.class);
    private final AuditEntityRepository auditEntityRepository;

    public AuditEntityServiceImpl(final AuditEntityRepository auditEntityRepository) {
        this.auditEntityRepository = auditEntityRepository;
    }

    @Override
    public AuditEntity saveAuditEntity(AuditEntity auditEntity) {
        logger.info("[{}] [{}] [saveAuditTrail()] request to save Audit Entity with entity id {}", Constants.SERVICE_NAME, Constants.INFO, auditEntity.getEntityId());
        return this.auditEntityRepository.save(auditEntity);
    }

    @Override
    public List<AuditEntity> findAuditEntityByEntityIdAndEntityType(Long entityId, String entityType) {
        logger.info("[{}] [{}] [findAuditTrailByEntityIdAndEntityType()] Request to find Audit Entity by entity Id {} And entity Type {}",
                Constants.SERVICE_NAME, Constants.INFO, entityId, entityType);
        return this.auditEntityRepository.findAuditTrailByEntityIdAndEntityType(entityId, entityType);
    }
}
