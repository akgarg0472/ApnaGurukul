package com.akgarg.apnagurukul.controllers.user;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.repository.UsersRepository;
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
    private final UsersRepository usersRepository;

    @Autowired
    public UserController(GetUserService getUserInformation,
                          UsersRepository usersRepository) {
        this.getUserInformation = getUserInformation;
        this.usersRepository = usersRepository;
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


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String myProfile(Principal principal, Model model) {
        if (principal != null) {
            Users user = getUserInformation.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("user", user);
                return "user/my-profile";
            }
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/update-profile", method = RequestMethod.GET)
    public String updateProfile(Principal principal, Model model) {
        if (principal != null) {
            Users user = getUserInformation.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("user", user);
                return "user/update-profile";
            }
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public String myNotifications(Principal principal, Model model) {
        if (principal != null) {
            Users user = getUserInformation.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("notifications", user.getNotifications());
                return "user/my-notifications";
            }
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/recent-activities", method = RequestMethod.GET)
    public String recentActivities(Principal principal, Model model) {
        if (principal != null) {
            Users user = getUserInformation.getUser(principal.getName());
            if (user == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("user", user);
                return "user/recent-activities";
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
