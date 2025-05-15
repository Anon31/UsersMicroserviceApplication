package tn.dev.usersmicroservice.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.dev.usersmicroservice.entities.User;
import tn.dev.usersmicroservice.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }
}
