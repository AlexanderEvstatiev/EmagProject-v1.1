package finalproject.emag.service;

import finalproject.emag.model.dto.*;
import finalproject.emag.model.entity.User;
import finalproject.emag.model.repository.UserRepository;
import finalproject.emag.util.PasswordEncoder;
import finalproject.emag.util.exception.*;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Email;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private static User user = new User(1L, "gosho@abv.bg", PasswordEncoder.hashPassword("12345678"), "Gosho" );

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLoggedUserInfo() {
        ViewUserDto view = userService.getLoggedUserInfo(user);
        Assert.assertEquals(user.getId(), view.getId());
    }

    @Test
    public void getUserForLogin() {
        UserLoginDto login = new UserLoginDto(user.getEmail(), "12345678");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        User saved = userService.getUserForLogin(login);
        Assert.assertEquals(user.getId(), saved.getId());
    }

    @Test(expected = MissingValuableFieldsException.class)
    public void getUserForLoginMissingFields() {
        UserLoginDto login = new UserLoginDto();
        login.setEmail("gosho@abv.bg");
        userService.getUserForLogin(login);
    }

    @Test(expected = WrongCredentialsException.class)
    public void getUserForLoginNoUser() {
        UserLoginDto login = new UserLoginDto(user.getEmail(), "12345678");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        userService.getUserForLogin(login);
    }

    @Test(expected = WrongCredentialsException.class)
    public void getUserForLoginWrongPassword() {
        user.setPassword(PasswordEncoder.hashPassword("12345678"));
        UserLoginDto login = new UserLoginDto(user.getEmail(), "123456789");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        userService.getUserForLogin(login);
    }

    @Test(expected = MissingValuableFieldsException.class)
    public void addUserMissingFields() {
        RegisterUserDto register = new RegisterUserDto();
        register.setEmail("gosho@abv.bg");
        userService.addUser(register);
    }

    @Test(expected = EmailInvalidFormatException.class)
    public void addUserInvalidEmail() {
        RegisterUserDto register = new RegisterUserDto();
        register.setEmail("blablabla");
        register.setFirstPassword("12345678");
        register.setSecondPassword("12345678");
        register.setName("Gosho");
        userService.addUser(register);
    }

    @Test(expected = PasswordWrongFormatException.class)
    public void addUserInvalidPassword() {
        RegisterUserDto register = new RegisterUserDto();
        register.setEmail("gosho@abv.bg");
        register.setFirstPassword("1234");
        register.setSecondPassword("1234");
        register.setName("Gosho");
        userService.addUser(register);
    }

    @Test(expected = PasswordsNotMatchingException.class)
    public void addUserNotMatchingPasswords() {
        RegisterUserDto register = new RegisterUserDto();
        register.setEmail("gosho@abv.bg");
        register.setFirstPassword("12345678");
        register.setSecondPassword("123456789");
        register.setName("Gosho");
        userService.addUser(register);
    }

    @Test(expected = EmailTakenException.class)
    public void addUserTakenEmail() {
        RegisterUserDto register = new RegisterUserDto();
        register.setEmail("gosho@abv.bg");
        register.setFirstPassword("123456789");
        register.setSecondPassword("123456789");
        register.setName("Gosho");
        Mockito.when(userRepository.findByEmail("gosho@abv.bg")).thenReturn(Optional.of(user));
        userService.addUser(register);
    }

    @Test
    public void editPersonalInfo() {
        EditPersonalInfoDto edit = new EditPersonalInfoDto();
        edit.setUsername("pesho");
        edit.setPhoneNumber("0878751999");
        Mockito.when(userRepository.findByUsername("pesho")).thenReturn(Optional.empty());
        userService.editPersonalInfoUser(edit, user);
        Assert.assertEquals(edit.getUsername(), user.getUsername());
    }

    @Test(expected = PhoneWrongFormatException.class)
    public void editPersonalInfoInvalidPhone() {
        EditPersonalInfoDto edit = new EditPersonalInfoDto();
        edit.setUsername("pesho");
        edit.setPhoneNumber("example");
        userService.editPersonalInfoUser(edit, user);
    }

    @Test(expected = UsernameTakenException.class)
    public void editPersonalInfoUsernameTaken() {
        EditPersonalInfoDto edit = new EditPersonalInfoDto();
        edit.setUsername("pesho");
        Mockito.when(userRepository.findByUsername("pesho")).thenReturn(Optional.of(user));
        userService.editPersonalInfoUser(edit, user);
    }

    @Test
    public void editEmail() {
        EditEmailDto edit = new EditEmailDto();
        edit.setEmail("gosho12@abv.bg");
        edit.setPassword("12345678");
        Mockito.when(userRepository.findByEmail("gosho12@abv.bg")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.editEmail(edit, user);
        Assert.assertEquals(edit.getEmail(), user.getEmail());
    }

    @Test(expected = EmailTakenException.class)
    public void editEmailTaken() {
        EditEmailDto edit = new EditEmailDto();
        edit.setEmail("gosho12@abv.bg");
        edit.setPassword("12345678");
        Mockito.when(userRepository.findByEmail("gosho12@abv.bg")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.editEmail(edit, user);
    }

    @Test(expected = WrongCredentialsException.class)
    public void editEmailWrongPassword() {
        user.setPassword(PasswordEncoder.hashPassword("12345678"));
        EditEmailDto edit = new EditEmailDto();
        edit.setEmail("gosho12@abv.bg");
        edit.setPassword("123456789");
        Mockito.when(userRepository.findByEmail("gosho12@abv.bg")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.editEmail(edit, user);
    }

    @Test
    public void editPassword() {
        EditPasswordDto edit = new EditPasswordDto();
        edit.setCurrentPassword("12345678");
        edit.setFirstPassword("123456789");
        edit.setSecondPassword("123456789");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.editPassword(edit, user);
        Assert.assertTrue(BCrypt.checkpw("123456789", user.getPassword()));
    }

    @Test(expected = MissingValuableFieldsException.class)
    public void editPasswordMissingFields() {
        EditPasswordDto edit = new EditPasswordDto();
        edit.setCurrentPassword("12345678");
        edit.setFirstPassword("123456789");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.editPassword(edit, user);
    }

    @Test(expected = WrongCredentialsException.class)
    public void editPasswordWrongPassword() {
        EditPasswordDto edit = new EditPasswordDto();
        edit.setCurrentPassword("1234567891");
        edit.setFirstPassword("123456789");
        edit.setSecondPassword("123456789");
        userService.editPassword(edit, user);
    }

    @Test(expected = PasswordWrongFormatException.class)
    public void editPasswordWrongFormat() {
        EditPasswordDto edit = new EditPasswordDto();
        edit.setCurrentPassword("12345678");
        edit.setFirstPassword("12345");
        edit.setSecondPassword("12345");
        userService.editPassword(edit, user);
    }

    @Test(expected = PasswordsNotMatchingException.class)
    public void editPasswordNotMatching() {
        EditPasswordDto edit = new EditPasswordDto();
        edit.setCurrentPassword("12345678");
        edit.setFirstPassword("123456789");
        edit.setSecondPassword("1234567891");
        userService.editPassword(edit, user);
    }

    @Test
    public void subscribe() {
        user.setSubscribed(false);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.subscribe(user);
        Assert.assertTrue(user.isSubscribed());
    }

    @Test(expected = AlreadySubscribedException.class)
    public void subscribeAlreadyDone() {
        user.setSubscribed(true);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.subscribe(user);
    }

    @Test
    public void unsubscribe() {
        user.setSubscribed(true);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.unsubscribe(user);
        Assert.assertFalse(user.isSubscribed());
    }

    @Test(expected = NotSubscribedException.class)
    public void unsubscribeAlreadyDone() {
        user.setSubscribed(false);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.unsubscribe(user);
    }
}
