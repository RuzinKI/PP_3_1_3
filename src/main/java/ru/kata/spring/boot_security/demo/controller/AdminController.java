package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping
    public String saveStudent(@ModelAttribute("user") User user) {
        System.out.println("save user");
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           Model model) {
        User userEdit = userService.getById(id);
        List<Role> roles = roleService.getAll();

        model.addAttribute("userV", userEdit);
        model.addAttribute("roles", roles);
        return "edit_user";
    }

    @PostMapping("/edit/{id}")
    public String editUserPost(@PathVariable Long id,
                               @ModelAttribute("user") User user) {
        user.setId(id);
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String createNewUser(Model model) {
        User user = new User();
        List<Role> roles = roleService.getAll();

        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "new_user";
    }
}
