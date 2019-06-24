package finalproject.emag.service;

import finalproject.emag.model.entity.User;
import finalproject.emag.model.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public UserService employeeService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = new User();
        user.setId(1);
        user.setEmail("something@abv.bg");
        user.setName("Kiro");
        user.setPassword("12345678");
        Mockito.when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setEmail("something@abv.bg");
        user.setName("Kiro");
        user.setPassword("12345678");
        service.addUser(user);
        Assertions.assertThat(user.equals(userRepository.findById(1L)));
    }
}
