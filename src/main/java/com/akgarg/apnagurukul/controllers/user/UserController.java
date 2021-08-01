package com.akgarg.apnagurukul.controllers.user;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.service.GetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final GetUserService getUserInformation;

    @Autowired
    public UserController(GetUserService getUserInformation) {
        this.getUserInformation = getUserInformation;
    }


    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpSession session, Principal principal, Model model) {
        if (session.getAttribute("buyBookLogin") != null) {
            session.removeAttribute("buyBookLogin");
            return "redirect:/buy-book";
        } else if (session.getAttribute("sellBookLogin") != null) {
            session.removeAttribute("sellBookLogin");
            return "redirect:/sell-book";
        }

        if (principal != null) {
            Users user = getUserInformation.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("username", user.getName());
                model.addAttribute("lastLoginDate", user.getLastLoginDate());
                model.addAttribute("lastLoginTime", user.getLastLoginTime());
                return "user/dashboard";
            }
        }
        return "redirect:/login";
    }


    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String facultyLogout() {
        return "redirect:/logout";
    }
}
