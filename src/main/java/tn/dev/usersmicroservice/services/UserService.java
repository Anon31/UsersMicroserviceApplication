package tn.dev.usersmicroservice.services;

import tn.dev.usersmicroservice.entities.Role;
import tn.dev.usersmicroservice.entities.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> findAllUsers();
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserById(Long id);
    Role createRole(Role role);
    User addRoleToUser(String username, String roleName);
}
