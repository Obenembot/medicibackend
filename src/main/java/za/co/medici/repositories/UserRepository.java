package za.co.medici.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.medici.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmailAndDeletedDateNull(String email);
    User findUserByIdAndDeletedDateNull(Long id);
    Page<User> findByDeletedDateNull(Pageable pageable);
}
