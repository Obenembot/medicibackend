package za.co.medici.dto;

import lombok.Data;

@Data
public class CreateUserDto {
    private Long id;
    private String email;
    private String firstName;
    private String surname;
    private String password;
}
