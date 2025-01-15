package za.co.medici.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.medici.dto.CreateUserDto;
import za.co.medici.dto.UseDto;
import za.co.medici.exceptions.UserException;
import za.co.medici.models.User;
import za.co.medici.repositories.UserRepository;
import za.co.medici.service.UserService;
import za.co.medici.uitls.BuilderUtil;
import za.co.medici.uitls.CheckUtil;
import za.co.medici.uitls.Constants;
import za.co.medici.uitls.EmailValidatorUtil;

import java.util.Base64;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final CheckUtil checkUtil;
    private final BuilderUtil builderUtil;
    private final EmailValidatorUtil emailValidatorUtil;

    public UserServiceImpl(final UserRepository userRepository,
                           final CheckUtil checkUtil,
                           final BuilderUtil builderUtil,
                           final EmailValidatorUtil emailValidatorUtil) {
        this.userRepository = userRepository;
        this.checkUtil = checkUtil;
        this.builderUtil = builderUtil;
        this.emailValidatorUtil = emailValidatorUtil;
    }

    @Override
    public User createUser(CreateUserDto userDto, String usernameHeader) throws UserException {
        logger.warn("[{}] [{}] [createUser()] Request to create User with email {} started", Constants.SERVICE_NAME, Constants.INFO, userDto.getEmail());
        User existinfUser = this.findUserByEmailAndDeletedDateNull(userDto.getEmail());
        if (!this.checkUtil.isEmpty(existinfUser)) {
            logger.warn("[{}] [{}] [createUser()] User with Email {} already exists", Constants.SERVICE_NAME, Constants.WARNING, userDto.getEmail());
            throw new UserException(String.format("User with Email %s already exists.", userDto.getEmail()), 400);
        }

        User user = new User();
        user.setPassword(encodePassword(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user = this.userRepository.save(this.builderUtil.buildCreate(user, usernameHeader));
        logger.warn("[{}] [{}] [createUser()] Request to create User with email {} completed", Constants.SERVICE_NAME, Constants.INFO, user.getEmail());
        return user;
    }

    @Override
    public User updateUser(UseDto useDto, String usernameHeader) throws UserException {
        logger.warn("[{}] [{}] [updateUser()] Request to update User with email {} started", Constants.SERVICE_NAME, Constants.INFO, useDto.getEmail());

        User existingUserByEmail = this.findUserByEmailAndDeletedDateNull(useDto.getEmail());

        User existinfUser = this.userRepository.findUserByIdAndDeletedDateNull(useDto.getId());
        if (this.checkUtil.isEmpty(existinfUser)) {
            logger.warn("[{}] [{}] [updateUser()] User with Id {} not found", Constants.SERVICE_NAME, Constants.WARNING, useDto.getId());
            throw new UserException(String.format("User with Id %s not found.", useDto.getId()), 404);
        }

        if (!this.checkUtil.isEmpty(existinfUser) && this.checkUtil.notEqual(existinfUser.getId(), useDto.getId())) {
            logger.warn("[{}] [{}] [updateUser()] User with ID {} in request and Db {} do not match  not found", Constants.SERVICE_NAME, Constants.WARNING, useDto.getId(), existinfUser.getId());
            throw new UserException(String.format("User with ID %d in request and Db %d do not match  not found", useDto.getId(), existinfUser.getId()), 404);
        }

        if (!this.checkUtil.isEmpty(existingUserByEmail)
                && this.checkUtil.notEqual(existingUserByEmail.getId(), existinfUser.getId())) {
            logger.warn("[{}] [{}] [updateUser()] Can not update User with Email {} fraudulent activity detected.", Constants.SERVICE_NAME, Constants.WARNING, useDto.getEmail());
            throw new UserException(String.format("Can not update User with Email %s fraudulent activity detected.", useDto.getEmail()), 404);
        }

        existinfUser.setEmail(useDto.getEmail());
        existinfUser.setFirstName(useDto.getFirstName());
        existinfUser.setSurname(useDto.getSurname());
        existinfUser = this.userRepository.save(this.builderUtil.buildUpdate(existinfUser, usernameHeader));
        logger.warn("[{}] [{}] [updateUser()] Request to update User with email {} completed", Constants.SERVICE_NAME, Constants.INFO, useDto.getEmail());
        return existinfUser;
    }

    @Override
    public User findUserByEmailAndDeletedDateNull(String email) throws UserException {
        logger.info("[{}] [{}] [findUserByEmail()] Request to find User with email: {} started", Constants.SERVICE_NAME, Constants.INFO, email);
        if (checkUtil.isEmpty(email)) {
            logger.warn("[{}] [{}] [findUserByEmail()] Can not request user with invalid email {}", Constants.SERVICE_NAME, Constants.WARNING, email);
            throw new UserException(String.format("Can not request user with invalid email: %s", email), 400);
        }

        if (!this.emailValidatorUtil.isValidEmail(email)){
            logger.warn("[{}] [{}] [findUserByEmail()] Can not request user with invalid email format {}", Constants.SERVICE_NAME, Constants.WARNING, email);
            throw new UserException(String.format("Can not request user with invalid email format: %s", email), 400);
        }
        User user = userRepository.findUserByEmailAndDeletedDateNull(email);
        logger.info("[{}] [{}] [updateUser()] Request to find User with email: {} complete", Constants.SERVICE_NAME, Constants.INFO, email);
        return user;
    }

    @Override
    public void deleteUserByEmail(String email, String usernameHeader) throws UserException {
        logger.info("[{}] [{}] [deleteUserByEmail()] Request to delete User with email: {} started", Constants.SERVICE_NAME, Constants.INFO, email);
        User userByEmail = this.findUserByEmailAndDeletedDateNull(email);

        if (!this.checkUtil.isEmpty(userByEmail)) {
            this.userRepository.save(this.builderUtil.buildDelete(userByEmail, usernameHeader));
            this.userRepository.save(userByEmail);
            logger.info("User with ID {} deleted successfully", id);
        } else {
            logger.warn("[{}] [{}] [deleteUserByEmail()] User with Email {} not found", Constants.SERVICE_NAME, Constants.WARNING, email);
            throw new UserException(String.format("User with Email %s not found.", email), 404);
        }
        logger.info("[{}] [{}] [deleteUserByEmail()] Request to delete User with email: {} completed", Constants.SERVICE_NAME, Constants.INFO, email);
    }

    @Override
    public User updateUserPassword(String email, String oldPassword, String newPassword) throws UserException {
        logger.warn("[{}] [{}] [updateUserPassword()] Request to update User Password with email {} started", Constants.SERVICE_NAME, Constants.INFO, email);

        User existinfUser = this.findUserByEmailAndDeletedDateNull(email);
        if (this.checkUtil.isEmpty(existinfUser)) {
            logger.warn("[{}] [{}] [updateUserPassword()] No User with Email {} Found", Constants.SERVICE_NAME, Constants.WARNING, email);
            throw new UserException(String.format("No User with Email %s Found.", email), 404);
        }
        if (this.checkUtil.notEqual(encodePassword(oldPassword), existinfUser.getPassword())) {
            logger.warn("[{}] [{}] [updateUserPassword()] Password do not Match For User with Email {}", Constants.SERVICE_NAME, Constants.WARNING, email);
            throw new UserException(String.format("Password do not Match For User with Email %s .", email), 404);
        }

        existinfUser.setPassword(this.encodePassword(newPassword));
        existinfUser = this.userRepository.save(this.builderUtil.buildUpdate(existinfUser, email));
        logger.warn("[{}] [{}] [updateUserPassword()] Request to update User Password with email {} completed", Constants.SERVICE_NAME, Constants.INFO, email);
        return existinfUser;
    }

    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
