package za.co.medici.uitls;

import org.springframework.stereotype.Component;
import za.co.medici.models.MultiEntity;

import java.time.Instant;

@Component
public class BuilderUtil {

    public <T extends MultiEntity> T buildCreate(T entity, String username) {
        Instant currentDate = this.getCurrentDate();
        entity.setCreatedDate(currentDate);
        entity.setLastUpdatedDate(currentDate);

        entity.setCreatedBy(username);
        entity.setLastUpdatedBy(username);

        entity.setDeletedDate(null);
        return entity;
    }


    public <T extends MultiEntity> T buildUpdate(T entity, String username) {
        Instant currentDate = this.getCurrentDate();
        entity.setLastUpdatedDate(currentDate);
        entity.setLastUpdatedBy(username);

        entity.setDeletedDate(null);
        return entity;
    }

    public <T extends MultiEntity> T buildDelete(T entity, String username) {
        Instant currentDate = this.getCurrentDate();

        entity.setLastUpdatedDate(currentDate);
        entity.setLastUpdatedBy(username);

        entity.setDeletedDate(currentDate);
        return entity;
    }

    private Instant getCurrentDate() {
        return Instant.now();
    }
}
