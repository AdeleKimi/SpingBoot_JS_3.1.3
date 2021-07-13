package ru.mikhailkuleshov.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mikhailkuleshov.springboot.model.User;
import ru.mikhailkuleshov.springboot.service.MyUserService;


import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {

    private final MyUserService userService;

    @Autowired
    public UserController(MyUserService userService) {
        this.userService = userService;
    }



    @GetMapping
    public String getUser(Principal principal, Model model) {
        User user = (User) userService.loadUserByUsername(principal.getName());

        model.addAttribute("user", user);



        return "/byIdForUser";
    }

}

