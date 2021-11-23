package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import app.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    //private UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserService userService/*, UserDetailsService userDetailsService*/) {
        this.userService = userService;
        //this.userDetailsService = userDetailsService;

    }

//    @GetMapping(value = "/")
//    public String getUserPage(ModelMap modelMap) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        modelMap.addAttribute("user", user);
//        return "userPage";
//    }



    @GetMapping(value = "/lk")
    public String getUserPage2(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "users/show";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.show(id));
        return "users/show";
    }
}
