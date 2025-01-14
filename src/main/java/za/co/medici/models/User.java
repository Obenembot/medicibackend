package za.co.medici.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.EntityListeners;
import za.co.medici.listener.AuditEntityListener;

@EntityListeners(AuditEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "User")
public class User extends MultiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

}

