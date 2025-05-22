package tn.dev.usersmicroservice.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tn.dev.usersmicroservice.entities.Address;
import tn.dev.usersmicroservice.dtos.AddressDTO;

import tn.dev.usersmicroservice.entities.User;
import tn.dev.usersmicroservice.entities.Role;
import tn.dev.usersmicroservice.exceptions.EmailAlreadyExistsException;
import tn.dev.usersmicroservice.register.RegistrationRequest;
import tn.dev.usersmicroservice.repositories.RoleRepository;
import tn.dev.usersmicroservice.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private Address toEntity(AddressDTO dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setNumber(dto.getNumber());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setZipCode(dto.getZipCode());
        address.setState(dto.getState());
        return address;
    }

    public User registerUser(RegistrationRequest request) {

        Optional<User> OptionalUser = userRepository.findByEmail(request.getEmail());

        if (OptionalUser.isPresent()) throw new EmailAlreadyExistsException("Email déjà utilisé !");

        User newUser = new User();
        newUser.setFirstname(request.getFirstname());
        newUser.setLastname(request.getLastname());
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setPhone(request.getPhone());
        newUser.setBirthDate(request.getBirthDate());
        newUser.setBillingAddress(toEntity(request.getBillingAddress()));
        newUser.setDeliveryAddress(toEntity(request.getDeliveryAddress()));
        newUser.setEnabled(false);

        userRepository.save(newUser);

        Role userRole = roleRepository.findByRole("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        newUser.setRoles(roles);



        return userRepository.save(newUser);
    }

//    @Override
//    public User findUserByEmail(String email) {
//        // Implementation for finding a user by email
//        return userRepository.findByEmail(email);
//    }

//    @Override
//    public Optional<User> findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
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
