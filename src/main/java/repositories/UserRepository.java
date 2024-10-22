package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
