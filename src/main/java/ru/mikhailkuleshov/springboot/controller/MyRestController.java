package ru.mikhailkuleshov.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mikhailkuleshov.springboot.model.Role;
import ru.mikhailkuleshov.springboot.model.User;
import ru.mikhailkuleshov.springboot.service.MyUserService;
import ru.mikhailkuleshov.springboot.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class MyRestController {

    private final UserService userService;


    @Autowired
    public MyRestController(MyUserService myUserService) {
        this.userService = myUserService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> allUsers() {
//        List<User> allUsers = userService.allUsers();

        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(Principal principal) {


        return new ResponseEntity<>(userService.loadUserByFirstName(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        userService.saveUser(user);

        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @PatchMapping("/change")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        userService.updateUser(user.getId(), user);

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);


    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.allRoles(), HttpStatus.OK);
    }

}
