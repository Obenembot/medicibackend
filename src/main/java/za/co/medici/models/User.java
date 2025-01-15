package za.co.medici.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.EntityListeners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.medici.listener.AuditEntityListener;
import za.co.medici.uitls.Constants;

import java.io.Serial;
import java.io.Serializable;

@EntityListeners(AuditEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends MultiEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            Logger logger = LoggerFactory.getLogger(User.class);
            logger.error("[{}] [{}] an exception with message {} occurred while converting to User",
                    Constants.SERVICE_NAME, Constants.ERROR, e.getMessage());
            return "{}";
        }
    }
}

