package app.controller;

import app.model.Role;
import app.model.User;
import app.service.RoleService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import org.springframework.ui.ModelMap;
import javax.validation.Valid;

@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/admin")
    public String welcome() {
        return "redirect:/admin/all";
    }

    @GetMapping(value = "admin/all")
    public String allUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/index";
    }

    @GetMapping("admin/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "users/show";
    }

    @GetMapping(value = "admin/add")
    public String addUser(Model model,
                          @ModelAttribute("user") User user,
                          @ModelAttribute("role") Role role) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users/new";
    }

    @PostMapping(value = "admin/add")
    public String postAddUser(@ModelAttribute("user") User user,
                              @RequestParam("rolesSelected") Long[] rolesId,
                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "users/new";
        }
        HashSet<Role> roles = new HashSet<>();
        for(Long roleId : rolesId) {
            roles.add(roleService.show(roleId));
        }
        String cryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(cryptedPassword);
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "admin/{id}/edit")
    public String editUser(ModelMap model, @PathVariable("id") Long id) {

        model.addAttribute("user", userService.show(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users/edit";
    }

    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         @RequestParam ("rolesSelected") Long[] rolesId,
                         BindingResult bindingResult//,
                         ) {
        if(bindingResult.hasErrors()) {
            return "users/edit";
        }
        HashSet<Role> roles = new HashSet<>();
        for(Long roleId : rolesId) {
            roles.add(roleService.show(roleId));
        }
        String cryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(cryptedPassword);
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
    return "redirect:/admin";
    }

}

