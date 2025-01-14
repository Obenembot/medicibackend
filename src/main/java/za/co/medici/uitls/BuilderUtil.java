package za.co.medici.uitls;

import org.springframework.stereotype.Component;
import za.co.medici.models.MultiEntity;

import java.time.Instant;

@Component
public class BuilderUtil {

    public <T extends MultiEntity> T buildCreate(T entity, String username) {
        Instant now = Instant.now();
        entity.setCreatedDate(now);
        entity.setLastUpdatedDate(now);
        entity.setCreatedBy(username);
        entity.setLastUpdatedBy(username);

        entity.setDeletedDate(null);
        return entity;
    }


    public <T extends MultiEntity> T buildUpdate(T entity, String username) {
        entity.setLastUpdatedDate(Instant.now());
        entity.setLastUpdatedBy(username);

        entity.setDeletedDate(null);
        return entity;
    }
}
