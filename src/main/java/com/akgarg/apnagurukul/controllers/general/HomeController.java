package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @ResponseBody
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home() {
        return "This is the home page of the apna gurukul";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "common/login";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("users", new Users());
        return "common/register";
    }
}
