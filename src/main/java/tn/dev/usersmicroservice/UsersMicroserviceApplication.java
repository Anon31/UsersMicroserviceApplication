package tn.dev.usersmicroservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tn.dev.usersmicroservice.entities.Role;
import tn.dev.usersmicroservice.entities.User;
import tn.dev.usersmicroservice.services.UserService;

@SpringBootApplication
public class UsersMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersMicroserviceApplication.class, args);
    }

    @Autowired
    UserService userService;

    @PostConstruct
    void init_users() {
        //Add roles
        userService.createRole(new Role(null, "ADMIN"));
        userService.createRole(new Role(null, "USER"));
        userService.createRole(new Role(null, "ANONYMOUS"));
        //ajouter les users
        userService.createUser(new User(null, "admin", "admin", true, null));
        userService.createUser(new User(null, "yapadlez", "password", true, null));
        userService.createUser(new User(null, "johndoe", "password", true, null));
        //ajouter les rôles aux users
        userService.addRoleToUser("admin", "ADMIN");
        userService.addRoleToUser("admin", "USER");
        userService.addRoleToUser("yapadlez", "USER");
        userService.addRoleToUser("johndoe", "USER");
    }


}
