package com.example.SpringPostgressDocker.controller;

import com.example.SpringPostgressDocker.model.Person;
import com.example.SpringPostgressDocker.repository.PersonRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---- LIST USERS ----
    @GetMapping("/users")
    public String listUsers(Model model) {
        Iterable<Person> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users"; // renders users.html
    }

    // ---- SHOW REGISTER FORM ----
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Person());
        return "register";
    }

    // ---- CREATE USER ----
    // PersonController.java (register part)
   @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") Person user,
                               BindingResult bindingResult,
                               Model model) {
        if (!bindingResult.hasFieldErrors("email")
            && userRepository.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "exists", "Email already in use");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        // hash password here
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        model.addAttribute("message", "Registration successful!");
        model.addAttribute("user", new Person()); // reset form
        return "register";
    }


    // ---- SHOW EDIT FORM ----
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Person> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "edit_user"; // create edit_user.html
        } else {
            model.addAttribute("message", "User not found!");
            return "redirect:/users";
        }
    }

    // ---- UPDATE USER ----
    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") Person updatedUser,
                             Model model) {
        Optional<Person> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            Person existingUser = userOpt.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            userRepository.save(existingUser);
            model.addAttribute("message", "User updated successfully!");
        }
        return "redirect:/users";
    }

    // ---- DELETE USER ----
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            model.addAttribute("message", "User deleted successfully!");
        }
        return "redirect:/users";
    }
}
