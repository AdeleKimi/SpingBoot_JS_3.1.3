package ru.mikhailkuleshov.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mikhailkuleshov.springboot.model.Role;
import ru.mikhailkuleshov.springboot.model.User;

import ru.mikhailkuleshov.springboot.service.MyUserService;
import ru.mikhailkuleshov.springboot.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")

public class AdminController {

    private final UserService userService;


    @Autowired
    public AdminController(MyUserService myUserService) {
        this.userService = myUserService;


    }

    @GetMapping()
    public String allUsers(Model model) {
//        User user = (User) userService.loadUserByFirstName(principal.getName());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("user",user);
        return "/AllUsers";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("newUser") User user,
                          @ModelAttribute("role") Role role,
                          Model model) {
//        User myUser = (User) userService.loadUserByFirstName(principal.getName());
        User myUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user",myUser);
        model.addAttribute("roles", userService.allRoles());

        return "/new";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") long id,
                              Model model) {
        model.addAttribute("user", userService.findUserById(id));


        return "/byId";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("newUser") User user,
                             @RequestParam("newroles") String[] role,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        Set<Role> set =  Arrays.stream(role).
                map(userService:: findRoleByName).
                collect(Collectors.toSet());
        user.setRoles(set);
        userService.saveUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findUserById(id));
        return "/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("newroles") String[] role,
                             BindingResult bindingResult,
                             @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }

        Set<Role> set =  Arrays.stream(role).
                map(userService:: findRoleByName).
                collect(Collectors.toSet());
        user.setRoles(set);

        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}

