package za.co.medici.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.medici.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
