package tn.dev.usersmicroservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dev.usersmicroservice.entities.User;
import tn.dev.usersmicroservice.entities.Role;
import tn.dev.usersmicroservice.repositories.RoleRepository;
import tn.dev.usersmicroservice.repositories.UserRepository;

import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createUser(User user) {
        // Encrypt the password before saving
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // Implementation for creating a user
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        // Implementation for finding all users
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        // Implementation for finding a user by username
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(Long id) {
        // Implementation for finding a user by ID
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Role createRole(Role role) {
        // Implementation for creating a role
        return roleRepository.save(role);
    }

    @Override
    public User addRoleToUser(String username, String roleName) {
        // Implementation for adding a role to a user
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRole(roleName);

        user.getRoles().add(role);
        // userRepository.save(user);

        return user;
    }
}
