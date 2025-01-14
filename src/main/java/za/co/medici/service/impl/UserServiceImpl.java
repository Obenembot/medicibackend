package za.co.medici.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.medici.exceptions.UserException;
import za.co.medici.models.User;
import za.co.medici.repositories.UserRepository;
import za.co.medici.service.UserService;
import za.co.medici.uitls.BuilderUtil;
import za.co.medici.uitls.CheckUtil;
import za.co.medici.uitls.Constants;

import java.util.Base64;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final CheckUtil checkUtil;
    private final BuilderUtil builderUtil;

    public UserServiceImpl(final UserRepository userRepository,
                           final CheckUtil checkUtil,
                           final BuilderUtil builderUtil) {
        this.userRepository = userRepository;
        this.checkUtil = checkUtil;
        this.builderUtil = builderUtil;
    }

    @Override
    public User createUser(User user, String usernameHeader) throws UserException {
        logger.warn("[{}] [{}] [createUser()] Request to create User with email {} started", Constants.SERVICE_NAME, Constants.INFO, user.getEmail());
        User existinfUser = this.findUserByEmail(user.getEmail());
        if (!this.checkUtil.isEmpty(existinfUser)) {
            logger.warn("[{}] [{}] [createUser()] User with Email {} already exists", Constants.SERVICE_NAME, Constants.WARNING, user.getEmail());
            throw new UserException(String.format("User with Email %s already exists.", user.getEmail()), 404);
        }

        base64EncodePassword(user);
        user = this.userRepository.save(this.builderUtil.buildCreate(user, usernameHeader));
        logger.warn("[{}] [{}] [createUser()] Request to create User with email {} completed", Constants.SERVICE_NAME, Constants.INFO, user.getEmail());
        return user;
    }

    @Override
    public User updateUser(User user, String usernameHeader) throws UserException {
        logger.warn("[{}] [{}] [updateUser()] Request to update User with email {} started", Constants.SERVICE_NAME, Constants.INFO, user.getEmail());

        User existinfUser = this.findUserByEmail(user.getEmail());
        if (this.checkUtil.isEmpty(existinfUser)) {
            logger.warn("[{}] [{}] [updateUser()] User with Email {} not found", Constants.SERVICE_NAME, Constants.WARNING, user.getEmail());
            throw new UserException(String.format("User with Email %s not found.", user.getEmail()), 404);
        }

        if (this.checkUtil.notEqual(existinfUser.getId(), user.getId())) {
            logger.warn("[{}] [{}] [updateUser()] User with ID {} in request and Db {} do not match  not found", Constants.SERVICE_NAME, Constants.WARNING, user.getId(), existinfUser.getId());
            throw new UserException(String.format("User with ID %d in request and Db %d do not match  not found", user.getId(), existinfUser.getId()), 404);
        }

        base64EncodePassword(user);
        user = this.userRepository.save(this.builderUtil.buildUpdate(user, usernameHeader));
        logger.warn("[{}] [{}] [updateUser()] Request to update User with email {} completed", Constants.SERVICE_NAME, Constants.INFO, user.getEmail());
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        logger.info("[{}] [{}] [findUserByEmail()] Request to find User with email: {} started", Constants.SERVICE_NAME, Constants.INFO, email);
        if (checkUtil.isEmpty(email)) {
            logger.warn("[{}] [{}] [findUserByEmail()] Can not request user with invalid email {}", Constants.SERVICE_NAME, Constants.WARNING, email);
            throw new UserException(String.format("Can not request user with invalid email: %s", email), 400);
        }
        User user = userRepository.findUserByEmail(email);
        logger.info("[{}] [{}] [updateUser()] Request to find User with email: {} complete", Constants.SERVICE_NAME, Constants.INFO, email);
        return user;
    }

    @Override
    public void deleteUserByEmail(String email, String usernameHeader) throws UserException {
        logger.info("[{}] [{}] [deleteUserByEmail()] Request to delete User with email: {} started", Constants.SERVICE_NAME, Constants.INFO, email);
        User userByEmail = this.findUserByEmail(email);

        if (!this.checkUtil.isEmpty(userByEmail)) {
            this.userRepository.save(this.builderUtil.buildUpdate(userByEmail, usernameHeader));
            this.userRepository.save(userByEmail);
            logger.info("User with ID {} deleted successfully", id);
        } else {
            logger.warn("[{}] [{}] [deleteUserByEmail()] User with Email {} not found", Constants.SERVICE_NAME, Constants.WARNING, email);
            throw new UserException(String.format("User with Email %s not found.", email), 404);
        }
        logger.info("[{}] [{}] [deleteUserByEmail()] Request to delete User with email: {} completed", Constants.SERVICE_NAME, Constants.INFO, email);
    }

    private void base64EncodePassword(User user) throws UserException {
        if (this.checkUtil.isEmpty(user) || this.checkUtil.isEmpty(user.getPassword())) {
            logger.info("[{}] [{}] [base64Encoded()] No Password provided for User with Email {}", Constants.SERVICE_NAME, Constants.INFO, user.getEmail());
            throw new UserException(String.format("No Password provided for User with Email %s", user.getEmail()), 400);
        }
        try {
            Base64.getDecoder().decode(user.getPassword());
        } catch (IllegalArgumentException e) {
            String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
            user.setPassword(encodedPassword);
        }
    }
}
