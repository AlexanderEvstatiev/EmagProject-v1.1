package finalproject.emag.util;

import finalproject.emag.model.dto.NotifyUserDto;
import finalproject.emag.model.pojo.entity.User;
import finalproject.emag.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.ArrayList;

@Component
public class DiscountNotify {

    @Autowired
    private UserRepository userRepository;

    public void notifyForPromotion(String title,String message) {
        ArrayList<NotifyUserDto> users = new ArrayList<>();
        for (User user : userRepository.findBySubscribedTrue()) {
            users.add(new NotifyUserDto(user.getEmail(), user.getName()));
        }
        new Thread(new Runnable() {
            @Override
            public void run(){
                for (NotifyUserDto user : users){
                    try {
                        MailUtil.sendMail("testingemag19@gmail.com",user.getEmail(),title,message);
                    } catch (MessagingException e) {
                        System.out.println("Ops there was a problem sending the email."+e.getMessage());
                    }
                }
            }
        }).start();
    }
}