package za.co.medici.service;

import za.co.medici.dto.UseDto;
import za.co.medici.exceptions.UserException;
import za.co.medici.models.User;

public interface UserService {

    User createUser(User user, String usernameHeader) throws UserException;

    User updateUser(UseDto useDto, String usernameHeader) throws UserException;

    User findUserByEmailAndDeletedDateNull(String email) throws UserException;

    void deleteUserByEmail(String email, String usernameHeader) throws UserException;

    User updateUserPassword(String email,String oldPassword, String newPassword) throws UserException;
}
