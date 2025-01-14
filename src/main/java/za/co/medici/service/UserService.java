package za.co.medici.service;

import za.co.medici.exceptions.UserException;
import za.co.medici.models.User;

public interface UserService {

    User createUser(User user, String usernameHeader) throws UserException;

    User updateUser(User user, String usernameHeader) throws UserException;

    User findUserByEmail(String email) throws UserException;

    void deleteUserByEmail(String email, String usernameHeader) throws UserException;
}
