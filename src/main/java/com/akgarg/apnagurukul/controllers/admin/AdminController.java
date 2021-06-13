package com.akgarg.apnagurukul.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @ResponseBody
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "This is the admin dashboard";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String adminLogout() {
        System.out.println("admin logout called");
        return "redirect:/logout";
    }
}
