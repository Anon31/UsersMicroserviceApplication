package tn.dev.usersmicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.dev.usersmicroservice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
