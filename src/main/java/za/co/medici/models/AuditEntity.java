package za.co.medici.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.medici.uitls.Constants;

import java.io.Serial;

@Getter
@Setter
@Entity
@Table(name = "User")
public class AuditEntity extends MultiEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "action", nullable = false)
    private String action;

    @NotNull
    @Lob
    @Column(name = "payload", nullable = false)
    private String payload;

    @NotNull
    @Column(name = "entityId", nullable = false)
    private Long entityId;

    @NotNull
    @Column(name = "entityType", nullable = false)
    private String entityType;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            Logger logger = LoggerFactory.getLogger(AuditEntity.class);
            logger.error("[{}] [{}] an exception with message {} occurred while converting to getLogger",
                    Constants.SERVICE_NAME, Constants.ERROR, e.getMessage());
            return "{}";
        }
    }
}
