package za.co.medici.uitls;

import org.springframework.stereotype.Component;
import za.co.medici.models.MultiEntity;

import java.time.LocalDateTime;

@Component
public class BuilderUtil {

    public <T extends MultiEntity> T buildCreate(T entity, String username) {
        LocalDateTime currentDate = this.getCurrentDate();
        entity.setCreatedDate(currentDate);
        entity.setLastUpdatedDate(currentDate);
        entity.setCreatedBy(username);
        entity.setLastUpdatedBy(username);
        entity.setDeletedDate(null);
        return entity;
    }


    public <T extends MultiEntity> T buildUpdate(T entity, String username) {
        LocalDateTime currentDate = this.getCurrentDate();
        entity.setLastUpdatedDate(currentDate);
        entity.setLastUpdatedBy(username);
        entity.setDeletedDate(null);
        return entity;
    }

    public <T extends MultiEntity> T buildDelete(T entity, String username) {
        LocalDateTime currentDate = this.getCurrentDate();
        entity.setLastUpdatedDate(currentDate);
        entity.setLastUpdatedBy(username);
        entity.setDeletedDate(currentDate);
        return entity;
    }

    private LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }
}
