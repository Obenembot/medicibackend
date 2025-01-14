package za.co.medici.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.medici.models.AuditEntity;

import java.util.List;

@Repository
public interface AuditEntityRepository extends JpaRepository<AuditEntity, Long> {

    List<AuditEntity> findAuditTrailByEntityIdAndEntityType(Long entityId, String entityType);
}
