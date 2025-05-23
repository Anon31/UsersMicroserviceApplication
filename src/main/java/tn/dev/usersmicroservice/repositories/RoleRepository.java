package tn.dev.usersmicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.dev.usersmicroservice.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);

    // Retrieves the role or throws a business exception. Centralizes Optional management in one place.
    default Role getByRoleOrThrow(String roleName) {
        return findByRole(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Le rôle " + roleName + " est introuvable !"));
    }
}
