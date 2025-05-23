package tn.dev.usersmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UsersMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersMicroserviceApplication.class, args);
    }
}

//package tn.dev.usersmicroservice;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import tn.dev.usersmicroservice.entities.Address;
//import tn.dev.usersmicroservice.entities.Role;
//import tn.dev.usersmicroservice.entities.User;
//import tn.dev.usersmicroservice.services.UserService;
//
//import java.time.LocalDate;
//
//@SpringBootApplication
//@EnableJpaAuditing
//public class UsersMicroserviceApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(UsersMicroserviceApplication.class, args);
//    }
//
//    @Autowired
//    UserService userService;
//
//    @PostConstruct
//    void init_users() {
//        //Add roles
//        userService.createRole(new Role(null, "ADMIN"));
//        userService.createRole(new Role(null, "USER"));
//        userService.createRole(new Role(null, "ANONYMOUS"));
//        // Add users
//        User admin = User.builder()
//                .firstname("Super")
//                .lastname("Admin")
//                .username("admin")
//                .password("admin")
//                .email("admin@franceweed.com")
//                .phone("0123456789")
//                .birthDate(LocalDate.of(1985, 12, 12))
//                .enabled(true)
//                .billingAddress(new Address("1", "Rue de la Paix", "Paris", "75002", "France"))
//                .deliveryAddress(new Address("1", "Rue de la Paix", "Paris", "75002", "France"))
//                .build();
//        userService.createUser(admin);
//
//        User yapadlez = User.builder()
//                .firstname("Thomas")
//                .lastname("Noël")
//                .username("yapadlez")
//                .password("yapadlez")
//                .email("thomas.noel.31@gmail.com")
//                .phone("0680243171")
//                .birthDate(LocalDate.of(1978, 12, 28))
//                .enabled(true)
//                .billingAddress(new Address("804", "Route de Sémalens", "Puylaurens", "81700", "France"))
//                .deliveryAddress(new Address("804", "Route de Sémalens", "Puylaurens", "81700", "France"))
//                .build();
//        userService.createUser(yapadlez);
//
//        User john = User.builder()
//                .firstname("John")
//                .lastname("Doe")
//                .username("johndoe")
//                .password("password")
//                .email("john.doe@gmail.com")
//                .phone("0652451584")
//                .birthDate(LocalDate.of(1988, 7, 22))
//                .enabled(true)
//                .billingAddress(new Address("12", "Avenue des Champs-Élysées", "Paris", "75008", "France"))
//                .deliveryAddress(new Address("12", "Avenue des Champs-Élysées", "Paris", "75008", "France"))
//                .build();
//        userService.createUser(john);
//        //ajouter les rôles aux users
//        userService.addRoleToUser("admin", "ADMIN");
//        userService.addRoleToUser("admin", "USER");
//        userService.addRoleToUser("yapadlez", "USER");
//        userService.addRoleToUser("johndoe", "USER");
//    }
//}
