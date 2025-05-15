package tn.dev.usersmicroservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.dev.usersmicroservice.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
