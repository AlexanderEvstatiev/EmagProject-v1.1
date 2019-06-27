package finalproject.emag.service;

import finalproject.emag.model.dto.*;
import finalproject.emag.model.entity.User;
import finalproject.emag.model.messages.MessageSuccess;
import finalproject.emag.model.repository.UserRepository;
import finalproject.emag.util.GetDate;
import finalproject.emag.util.PasswordEncoder;
import finalproject.emag.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ViewUserDto getLoggedUserInfo(User user){
        return new ViewUserDto(user.getId(),user.getEmail(),user.getName(),user.getUsername(),
                user.getPhoneNumber(),user.getBirthDate(),user.isSubscribed(),user.getImageUrl());
    }

    public User getUserForLogin(UserLoginDto input) {
        checkLoginInputFields(input);
        Optional<User> user = userRepository.findByEmail(input.getEmail());
        if(user.isPresent()) {
            User savedUser = user.get();
            checkLoginPassword(input.getPassword(), savedUser.getPassword());
            return savedUser;
        }
        throw new WrongCredentialsException();
    }

    @Transactional
    public User addUser(RegisterUserDto input) {
        User user = getUserForRegister(input);
        checkIfEmailIsFree(user.getEmail());
        user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public void editPersonalInfoUser(EditPersonalInfoDto input, User user) {
        checkIfUsernameExists(input.getUsername());
        user.setName(input.getName() == null ? user.getName() : input.getName());
        user.setUsername(input.getUsername() == null ? user.getUsername() : input.getUsername());
        user.setPhoneNumber(input.getPhoneNumber() == null ? user.getPhoneNumber() : checkPhone(input.getPhoneNumber()));
        user.setBirthDate(input.getBirthDate() == null ? user.getBirthDate() : GetDate.getDate(input.getBirthDate()));
        userRepository.save(user);
    }

    @Transactional
    public void editEmail(EditEmailDto input, User user) {
        checkIfEmailIsFree(input.getEmail());
        checkCorrectPassword(user.getPassword(), input.getPassword());
        user.setEmail(input.getEmail());
        userRepository.save(user);
    }

    @Transactional
    public void editPassword(EditPasswordDto input, User user) {
        checkEditPasswordInputFields(input);
        checkCorrectPassword(user.getPassword(), input.getCurrentPassword());
        checkPasswordFormat(input.getFirstPassword());
        checkIfPasswordsMatch(input.getFirstPassword(), input.getSecondPassword());
        user.setPassword(PasswordEncoder.hashPassword(input.getFirstPassword()));
        userRepository.save(user);
    }

    public MessageSuccess subscribe(User user) {
        if(user.isSubscribed()) {
            throw new AlreadySubscribedException();
        }
        user.setSubscribed(true);
        userRepository.save(user);
        return new MessageSuccess("You are now subscribed.",LocalDateTime.now());

    }

    public MessageSuccess unsubscribe (User user) {
        if(!user.isSubscribed()) {
            throw new NotSubscribedException();
        }
        user.setSubscribed(false);
        userRepository.save(user);
        return new MessageSuccess("You are now unsubscribed.",LocalDateTime.now());
    }

    private User getUserForRegister(RegisterUserDto input) {
        checkRegisterInputFields(input);
        checkEmail(input.getEmail());
        checkPasswordFormat(input.getFirstPassword());
        checkIfPasswordsMatch(input.getFirstPassword(), input.getSecondPassword());
        return User.builder().email(input.getEmail()).name(input.getName()).password(input.getFirstPassword())
                .subscribed(input.isSubscribed()).build();
    }

    private void checkIfPasswordsMatch(String firstPassword, String secondPassword) {
        if (!firstPassword.equals(secondPassword)) {
            throw new PasswordsNotMatchingException();
        }
    }

    private void checkEmail(String email) throws EmailInvalidFormatException {
        String emailRegex = "([A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\\.[A-Za-z]+)+)";
        if (!email.matches(emailRegex)) {
            throw new EmailInvalidFormatException();
        }
    }

    private void checkPasswordFormat(String password) throws PasswordWrongFormatException {
        String passwordRegex = "^(?=\\S+$).{8,}$";
        if (!password.matches(passwordRegex)) {
            throw new PasswordWrongFormatException();
        }
    }

    private void checkRegisterInputFields(RegisterUserDto input) {
        if(input.getEmail() == null || input.getName() == null
                || input.getFirstPassword() == null || input.getSecondPassword() == null) {
            throw new MissingValuableFieldsException();
        }
    }

    private void checkLoginPassword(String input, String password) {
        if(!BCrypt.checkpw(input, password)){
            throw new WrongCredentialsException();
        }
    }

    private void checkLoginInputFields(UserLoginDto input) {
        if (input.getEmail() == null || input.getPassword() == null) {
            throw new MissingValuableFieldsException();
        }
    }

    private void checkIfEmailIsFree(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            throw new EmailTakenException();
        }
    }

    private void checkIfUsernameExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            throw new UsernameTakenException();
        }
    }

    private String checkPhone(String phone) {
        String phoneRegex = "08[789]\\d{7}";
        if (phone.matches(phoneRegex)) {
            return phone;
        }
        throw new PhoneWrongFormatException();
    }

    private void checkCorrectPassword(String savedPassword, String inputPassword) {
        if(!BCrypt.checkpw(inputPassword, savedPassword)){
            throw new WrongCredentialsException();
        }
    }

    private void checkEditPasswordInputFields(EditPasswordDto input) {
        if (input.getCurrentPassword() == null || input.getFirstPassword() == null
                || input.getSecondPassword() == null) {
            throw new MissingValuableFieldsException();
        }
    }
}