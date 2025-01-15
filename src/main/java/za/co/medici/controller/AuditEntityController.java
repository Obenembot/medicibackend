package za.co.medici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.medici.exceptions.NoAuditEntityException;
import za.co.medici.models.AuditEntity;
import za.co.medici.service.AuditEntityService;
import za.co.medici.uitls.CheckUtil;
import za.co.medici.uitls.Constants;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3000/"})
@RestController
@RequestMapping("/api/audit-entity")
@Tag(name = "Audit Entity Management", description = "API for managing audit entity")
public class AuditEntityController {

    private static final Logger logger = LoggerFactory.getLogger(AuditEntityController.class);
    private final AuditEntityService auditEntityService;
    private final CheckUtil checkUtil;

    public AuditEntityController(final AuditEntityService auditEntityService,
                                 final CheckUtil checkUtil) {
        this.auditEntityService = auditEntityService;
        this.checkUtil = checkUtil;
    }

    /**
     * Retrieves a list of audit entity for a specific entity ID and entity type.
     *
     * @param entityId   The ID of the entity for which audit entity are requested.
     * @param entityType The type of the entity for which audit entity are requested.
     * @return A list of {@link AuditEntity} objects.
     * @throws NoAuditEntityException If no audit entity are found for the specified criteria.
     */
    @GetMapping("/")
    @Operation(summary = "Find Audit Entity by Entity", description = "Retrieves audit entity by entity ID and entity type.")
    public ResponseEntity<List<AuditEntity>> findAuditEnityByEntityIdAndEntityType(
            @RequestParam @Parameter(description = "The ID of the entity.") Long entityId,
            @RequestParam @Parameter(description = "The type of the entity. ") String entityType) throws NoAuditEntityException {
        logger.info("\n[{}] [{}] [findAuditEnityByEntityIdAndEntityType()] Find Remittance By entity Id {} and Entity Type {}", Constants.SERVICE_NAME, Constants.INFO, entityId, entityType);

        List<AuditEntity> auditTrails = this.auditEntityService.findAuditEntityByEntityIdAndEntityType(entityId, entityType);

        if (this.checkUtil.isEmpty(auditTrails)) {
            logger.info("[{}] [{}] [findAuditEnityByEntityIdAndEntityType] No AuditTrail Found For Provided Entity Id {} and Entity Type {}",
                    Constants.SERVICE_NAME, Constants.INFO, entityId, entityType);
            throw new NoAuditEntityException(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
        }

        logger.info("[{}] [{}] [findAuditEnityByEntityIdAndEntityType()] Found {} Audit Entity By Entity Id {} and Entity Type {}\n", Constants.SERVICE_NAME, Constants.INFO, auditTrails.size(), entityId, entityType);
        return ResponseEntity.ok(auditTrails);
    }
}
