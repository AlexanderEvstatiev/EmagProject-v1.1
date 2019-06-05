package finalproject.emag.model.repository;

import finalproject.emag.model.pojo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);

    List<User> findBySubscribedTrue();
}
