package tn.dev.usersmicroservice.services;

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
import tn.dev.usersmicroservice.exceptions.UsernameAlreadyExistsException;
import tn.dev.usersmicroservice.register.RegistrationRequest;
import tn.dev.usersmicroservice.repositories.RoleRepository;
import tn.dev.usersmicroservice.repositories.UserRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User registerUser(RegistrationRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Ce nom d'utilisateur est déjà associée à un compte.");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Cette adresse e-mail est déjà associée à un compte.");
        }

        LocalDate birth = request.getBirthDate()
                .withOffsetSameInstant(ZoneOffset.UTC)
                .toLocalDate();

        User newUser = new User();
        newUser.setFirstname(request.getFirstname());
        newUser.setLastname(request.getLastname());
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setPhone(request.getPhone());
        newUser.setBirthDate(birth);
        newUser.setBillingAddress(toEntity(request.getBillingAddress()));
        newUser.setDeliveryAddress(toEntity(request.getDeliveryAddress()));
        newUser.setEnabled(false);

        userRepository.save(newUser);

        Role userRole = roleRepository.getByRoleOrThrow("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
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



















    @Override
    public List<User> findAllUsers() {
        // Implementation for finding all users
        return userRepository.findAll();
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

//    @Override
//    public User addRoleToUser(String username, String roleName) {
//        // Implementation for adding a role to a user
//        User user = userRepository.findByUsername(username);
//        Role role = roleRepository.findByRole(roleName);
//
//        user.getRoles().add(role);
//        // userRepository.save(user);
//
//        return user;
//    }

    @Override
    @Transactional
    public User addRoleToUser(String username, String roleName) {
        // 1) Récupération de l'utilisateur
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur introuvable avec le username : " + username
                ));

        // 2) Récupération du rôle
        Role role = roleRepository.findByRole(roleName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Rôle introuvable : " + roleName
                ));

        // 3) Ajout du rôle à la collection
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }

        // 4) Sauvegarde et retour
        return userRepository.save(user);
    }

}
