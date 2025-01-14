package za.co.medici.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.medici.models.AuditEntity;
import za.co.medici.service.AuditEntityService;
import za.co.medici.uitls.BuilderUtil;
import za.co.medici.uitls.Constants;

import java.lang.reflect.Field;

@Component
public class AuditEntityListener<T> {
    private static final Logger logger = LoggerFactory.getLogger(AuditEntityListener.class);

    private static BuilderUtil builderUtil;
    private static AuditEntityService auditEntityService;


    @Autowired
    public void setGenericEntityListener(BuilderUtil builderUtil, AuditEntityService auditEntityService) {
        AuditEntityListener.builderUtil = builderUtil;
        AuditEntityListener.auditEntityService = auditEntityService;
    }


    @PostPersist
    public void postPersist(T entity) {
        logger.info("[{}] [{}] [postPersist()] creating new audit entity started {}", Constants.SERVICE_NAME, Constants.INFO, entity);
        Class<?> aClass = entity.getClass();
        AuditEntity auditEntity = buildAuditTrial(entity, aClass, "CREATE");
        auditEntityService.saveAuditEntity(builderUtil.buildCreate(auditEntity, Constants.SYSTEM));
        logger.info("[{}] [{}]  creating new audit entity completed {}", Constants.SERVICE_NAME, Constants.INFO, entity);
    }

    @PostUpdate
    public void postUpdate(T entity) {
        logger.info("[{}] [{}] [postUpdate()] update audit entity started {}", Constants.SERVICE_NAME, Constants.INFO, entity);
        Class<?> aClass = entity.getClass();
        AuditEntity auditEntity = buildAuditTrial(entity, aClass, "UPDATE");
        auditEntityService.saveAuditEntity(builderUtil.buildCreate(auditEntity, Constants.SYSTEM));
        logger.info("[{}] [{}] [postUpdate()] update audit entity completed {}", Constants.SERVICE_NAME, Constants.INFO, entity);
    }

    AuditEntity buildAuditTrial(T entity, Class<?> aClass, String typeOfLog) {
        try {
            AuditEntity auditTrial = new AuditEntity();
            Field customerIdField = aClass.getDeclaredField("id");
            customerIdField.setAccessible(true);
            Long customerId = (Long) customerIdField.get(entity);
            auditTrial.setEntityId(customerId);
            auditTrial.setEntityType(aClass.getName());
            auditTrial.setPayload(entity.toString());
            auditTrial.setAction(typeOfLog);

            return auditTrial;
        } catch (Exception e) {
            logger.error("[{}] [{}] [buildAuditTrial()] error occurred while converting Audit Entity {}", Constants.SERVICE_NAME, Constants.ERROR, e.getMessage());
            return null;
        }
    }
}
