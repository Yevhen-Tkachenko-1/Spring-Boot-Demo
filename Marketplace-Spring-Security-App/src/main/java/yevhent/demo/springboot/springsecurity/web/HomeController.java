package yevhent.demo.springboot.springsecurity.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String getHome(Model model){
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
}
