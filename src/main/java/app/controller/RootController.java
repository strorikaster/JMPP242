package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RootController {
    @RequestMapping(value = "/")
    public String getHomePage(Model model) {
        List<String> messages = new ArrayList<>();
        //messages.add("Hello!");
        messages.add("It's CRUD-Spring-MVC-SECURITY application");
        messages.add("This is Main Page");
        model.addAttribute("messages", messages);
        return "users/helloPage";
    }

//    @GetMapping(value = "login")
//    public String getLoginPage() {
//        return "users/loginPage";
//    }

    @GetMapping(value = "vip")
    public String getVipPage(Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("This is VIP Page");
        model.addAttribute("messages", messages);
        return "users/vipPage";
    }
}
