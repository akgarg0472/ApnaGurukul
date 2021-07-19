package com.akgarg.apnagurukul.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpSession session) {
        if (session.getAttribute("buyBookLogin") != null) {
            session.removeAttribute("buyBookLogin");
            return "redirect:/buy-book";
        } else if (session.getAttribute("sellBookLogin") != null) {
            session.removeAttribute("sellBookLogin");
            return "redirect:/sell-book";
        }

        return "user/dashboard";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String facultyLogout() {
        System.out.println("user logout called");
        //noinspection SpringMVCViewInspection
        return "redirect:/logout";
    }
}
