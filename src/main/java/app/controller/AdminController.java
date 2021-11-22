package app.controller;

import app.model.Role;
import app.model.User;
import app.service.RoleService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;

//@Controller
//@RequestMapping("/users")
//public class AdminController {
//
//    private UserService userService;
//    private RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping()
//    public String index(Model model) {
//    //Получим всех людей из Dao и передадим на отображение в представление
//        model.addAttribute("users", userService.getAllUsers());
//        return "users/index";
//    }
//
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.show(id));
//        return "users/show";
//    }
//
//@GetMapping("/new")
//public String newUser(Model model,
//                      @ModelAttribute("user") User user,
//                      @ModelAttribute("role") Role role) {
//    model.addAttribute("allRoles" , roleService.getAllRoles());
//    return "users/new";
//}
//
//    @PostMapping()
//    public String create(@ModelAttribute("user") @Valid User user,
//                         @RequestParam ("rolesSelected") Long[] rolesId,
//                         BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            return "users/new";
//        }
//        HashSet<Role> roles = new HashSet<>();
//        for(Long roleId : rolesId) {
//            roles.add(roleService.show(roleId));
//        }
//        user.setRoles(roles);
//        userService.save(user);
//        return "redirect:/users";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.show(id));
//        model.addAttribute("allRoles" , roleService.getAllRoles());
//        return "users/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") @Valid User user,
//                         @RequestParam ("rolesSelected") Long[] rolesId,
//                         BindingResult bindingResult//,
//                         /*@PathVariable("id") Long id*/) {
//        if(bindingResult.hasErrors()) {
//            return "users/edit";
//        }
//        HashSet<Role> roles = new HashSet<>();
//        for(Long roleId : rolesId) {
//            roles.add(roleService.show(roleId));
//        }
//        user.setRoles(roles);
//        userService.update(user);
//        return "redirect:/users";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        userService.delete(id);
//        return "redirect:/users";
//    }
//
//    @GetMapping(value = "/login")
//    public String loginPage() {
//        return "users/login";
//    }
//}
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import app.model.Role;
import app.model.User;
import app.service.RoleService;
import app.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    @GetMapping(value = "admin/add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/new";
    }

    @PostMapping(value = "admin/add")
    public String postAddUser(@ModelAttribute("user") User user,
                              @RequestParam(required=false) String roleAdmin,
                              @RequestParam(required=false) String roleVIP,
                              @RequestParam(required=false) String roleUser)
    {
        Set<Role> roles = new HashSet<>();

//        if (roleUser != null && roleUser.equals("USER")) {
//            roles.add(roleService.getRoleByName("USER"));
//        }
//       // roles.add(roleService.getRoleByName("USER"));
//        if (roleAdmin != null && roleAdmin.equals("ADMIN")) {
//            roles.add(roleService.getRoleByName("ADMIN"));
//        }
//        if (roleVIP != null && roleVIP.equals("VIP")) {
//            roles.add(roleService.getRoleByName("VIP"));
//        }
        user.setRoles(roles);
        userService.save(user);

        return "redirect:/admin";
    }


    @GetMapping(value = "admin/edit/{id}")
    public String editUser(ModelMap model, @PathVariable("id") Long id) {
        User user = userService.show(id);
        Set<Role> roles = user.getRoles();
//        for (Role role: roles) {
//            if (role.equals(roleService.getRoleByName("USER"))) {
//                model.addAttribute("roleUser", true);
//            }
//            if (role.equals(roleService.getRoleByName("ADMIN"))) {
//                model.addAttribute("roleAdmin", true);
//            }
//            if (role.equals(roleService.getRoleByName("VIP"))) {
//                model.addAttribute("roleVIP", true);
//            }
//        }
        model.addAttribute("user", user);
        return "users/edit";
    }
    @PostMapping(value = "admin/edit")
    public String postEditUser(@ModelAttribute("user") User user,
                               @RequestParam(required=false) String roleAdmin,
                               @RequestParam(required=false) String roleVIP,
                               @RequestParam(required=false) String roleUser) {

        Set<Role> roles = new HashSet<>();
//        //roles.add(roleService.getRoleByName("USER"));
//        if (roleUser != null && roleUser .equals("USER")) {
//            roles.add(roleService.getRoleByName("USER"));
//        }
//        if (roleAdmin != null && roleAdmin .equals("ADMIN")) {
//            roles.add(roleService.getRoleByName("ADMIN"));
//        }
//        if (roleVIP != null && roleVIP.equals("VIP")) {
//            roles.add(roleService.getRoleByName("VIP"));
//        }
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}

