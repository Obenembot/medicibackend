package za.co.medici.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import za.co.medici.dto.CreateUserDto;
import za.co.medici.dto.UseDto;
import za.co.medici.exceptions.UserException;
import za.co.medici.models.User;

public interface UserService {

    User createUser(CreateUserDto useDto, String usernameHeader) throws UserException;

    User updateUser(UseDto useDto, String usernameHeader) throws UserException;

    User findUserByEmailAndDeletedDateNull(String email) throws UserException;
    Page<User> findAllUserAndDeletedDateNull(Pageable pageable);

    void deleteUserByEmail(String email, String usernameHeader) throws UserException;

    User updateUserPassword(String email,String oldPassword, String newPassword) throws UserException;
}
