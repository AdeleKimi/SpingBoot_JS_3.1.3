package ru.mikhailkuleshov.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mikhailkuleshov.springboot.model.Role;
import ru.mikhailkuleshov.springboot.model.User;

import ru.mikhailkuleshov.springboot.service.MyUserService;


@Controller
@RequestMapping("/admin")

public class AdminController {

    private final MyUserService myUserService;


    @Autowired
    public AdminController(MyUserService myUserService) {
        this.myUserService = myUserService;


    }

    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", myUserService.allUsers());
        return "/AllUsers";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "/new";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") long id,
                              Model model) {
        model.addAttribute("user", myUserService.findUserById(id));


        return "/byId";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }


        myUserService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", myUserService.findUserById(id));
        return "/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, BindingResult bindingResult,
                             @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }

        myUserService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        myUserService.deleteUser(id);
        return "redirect:/admin";
    }


}

