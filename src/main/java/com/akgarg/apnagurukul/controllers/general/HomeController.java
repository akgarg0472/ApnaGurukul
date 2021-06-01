package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {

    private final UsersRepository usersRepository;

    @Autowired
    public HomeController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @ResponseBody
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home() {
        return "This is the home page of the apna gurukul";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal) {
        Users users = this.usersRepository.getUserByUsername(principal.getName());

        if (users != null) {
            String role = users.getRole();

            switch (role) {
                case "ROLE_ADMIN":
                    return "redirect:/admin/dashboard";

                case "ROLE_FACULTY":
                    return "redirect:/faculty/dashboard";

                case "ROLE_STUDENT":
                    return "redirect:/student/dashboard";
            }
        }

        return "common/login";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("users", new Users());
        return "common/register";
    }


    @RequestMapping(value = "/logout-success", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.setAttribute("logoutSuccess", "Logout successful");
        return "redirect:/login";
    }
}