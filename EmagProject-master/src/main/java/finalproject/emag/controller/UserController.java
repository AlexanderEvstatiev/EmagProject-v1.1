package finalproject.emag.controller;

import finalproject.emag.model.dto.*;
import finalproject.emag.model.pojo.entity.User;
import finalproject.emag.model.pojo.messages.MessageSuccess;
import finalproject.emag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/users",produces = "application/json")
public class UserController extends BaseController {

    private static final String USER = "user";

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ViewUserDto loginUser(@RequestBody UserLoginDto input, HttpSession session) {
        validateAlreadyLogged(session);
        User user = this.userService.getUserForLogin(input);
        session.setAttribute(USER, user);
        session.setMaxInactiveInterval((60 * 60));
        return userService.getLoggedUserInfo(user);
    }

    @PostMapping(value = "/register")
    public ViewUserDto registerUser(@RequestBody RegisterUserDto input, HttpSession session) {
        validateAlreadyLogged(session);
        User user = userService.getUserForRegister(input);
        this.userService.addUser(user);
        session.setAttribute(USER, user);
        session.setMaxInactiveInterval((60 * 60));
        return userService.getLoggedUserInfo(user);
    }

    @PostMapping(value = "/logout")
    public MessageSuccess logoutUser(HttpSession session) {
        validateLogin(session);
        session.invalidate();
        return new MessageSuccess("You logged out", LocalDateTime.now());
    }

    @PutMapping(value = "/subscribe")
    public MessageSuccess subscribe(HttpSession session) {
        validateLogin(session);
        return this.userService.subscribe((User)session.getAttribute(USER));
    }

    @PutMapping(value = "/unsubscribe")
    public MessageSuccess unsubscribe(HttpSession session) {
        validateLogin(session);
        return this.userService.unsubscribe((User)session.getAttribute(USER));
    }

    @PutMapping(value = "/edit-personal-info")
    public ViewUserDto editPersonalInfoUser(@RequestBody EditPersonalInfoDto input,HttpSession session) {
        validateLogin(session);
        User user = (User)session.getAttribute(USER);
        this.userService.editPersonalInfoUser(input, user);
        return userService.getLoggedUserInfo(user);
    }

    @PutMapping(value = "/edit-email")
    @Transactional
    public ViewUserDto editUserSecurity(@RequestBody EditEmailDto input, HttpSession session) {
        validateLogin(session);
        User user = (User)session.getAttribute(USER);
        this.userService.editEmail(input, user);
        return userService.getLoggedUserInfo(user);
    }

    @PutMapping(value = "/edit-password")
    @Transactional
    public MessageSuccess editPassword(@RequestBody EditPasswordDto input, HttpSession session) {
        validateLogin(session);
        User user = (User)session.getAttribute(USER);
        this.userService.editPassword(input, user);
        return new MessageSuccess("Edit successful", LocalDateTime.now());
    }
}