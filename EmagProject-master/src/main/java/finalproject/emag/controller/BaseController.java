package finalproject.emag.controller;

import finalproject.emag.model.entity.User;
import finalproject.emag.model.messages.ErrorMsg;
import finalproject.emag.util.AttributeUtil;
import finalproject.emag.util.exception.*;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
public abstract class BaseController {

    private static final String USER = AttributeUtil.USER.getValue();

    private static Logger log = Logger.getLogger(BaseController.class.getName());

    @ExceptionHandler({NotLoggedException.class, NotAdminException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMsg handleNotLogged(Exception e){
        log.error("exception: "+e);
        return new ErrorMsg(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
    }

    @ExceptionHandler({WrongCredentialsException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMsg missingFields(Exception e){
        log.error("exception: "+e);
        return new ErrorMsg(e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMsg invalidInput(Exception e){
        log.error("exception: "+e);
        return new ErrorMsg(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }


    @ExceptionHandler({BaseException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMsg handleMyErrors(Exception e){
        log.error("exception: "+e);
        return new ErrorMsg(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }

    @ExceptionHandler({ParseException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMsg dateParser(Exception e){
        return new ErrorMsg(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }

    void validateLogin(HttpSession session) {
        if(session.getAttribute(USER) == null){
            throw new NotLoggedException();
        }
    }

    void validateAlreadyLogged(HttpSession session) {
        if (session.getAttribute(USER) != null) {
            throw new AlreadyLoggedException();
        }
    }

    void validateLoginAdmin(HttpSession session) {
        validateLogin(session);
        User logged = (User) (session.getAttribute(USER));
        if(!logged.isAdmin()){
            throw new NotAdminException();
        }
    }
}