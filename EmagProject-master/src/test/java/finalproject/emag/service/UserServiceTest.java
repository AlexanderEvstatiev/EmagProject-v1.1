package finalproject.emag.service;

import finalproject.emag.model.dto.RegisterUserDto;
import finalproject.emag.model.dto.UserLoginDto;
import finalproject.emag.model.dto.ViewUserDto;
import finalproject.emag.model.entity.User;
import finalproject.emag.model.repository.UserRepository;
import finalproject.emag.util.PasswordEncoder;
import finalproject.emag.util.exception.MissingValuableFieldsException;
import finalproject.emag.util.exception.WrongCredentialsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLoggedUserInfo() {
        User user = createUser();
        ViewUserDto view = userService.getLoggedUserInfo(user);
        Assert.assertEquals(user.getId(), view.getId());
    }

    @Test
    public void getUserForLogin() {
        User user = createUser();
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
        User user = createUser();
        UserLoginDto login = new UserLoginDto(user.getEmail(), "12345678");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        userService.getUserForLogin(login);
    }

    @Test(expected = WrongCredentialsException.class)
    public void getUserForLoginWrongPassword() {
        User user = createUser();
        UserLoginDto login = new UserLoginDto(user.getEmail(), "123456789");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        userService.getUserForLogin(login);
    }

    //TODO FIX
    @Test
    public void addUser() {
        User user = createUser();
        RegisterUserDto register = new RegisterUserDto();
        register.setEmail("gosho@abv.bg");
        register.setName("Gosho");
        register.setFirstPassword("12345678");
        register.setSecondPassword("12345678");
        user.setPassword("12345678");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User saved = userService.addUser(register);
        Assert.assertEquals(user.getId(), saved.getId());
    }


    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("gosho@abv.bg");
        user.setPassword(PasswordEncoder.hashPassword("12345678"));
        user.setName("Gosho");
        return user;
    }
}
