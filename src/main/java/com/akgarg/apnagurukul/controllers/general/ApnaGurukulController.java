package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class ApnaGurukulController {

    private final UsersRepository usersRepository;

    @Autowired
    public ApnaGurukulController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home() {
        return "common/home";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal) {
        if (principal != null) {
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
        }

        return "common/login";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("users", new Users());
        return "common/register";
    }


    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String forgotPassword() {
        return "common/forgot-password";
    }


    @RequestMapping(value = "/logout-success", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.setAttribute("logoutSuccess", "Logout successful");
        return "redirect:/login";
    }


    @RequestMapping(value = "/verify-otp", method = RequestMethod.GET)
    public String verifyOtp(HttpSession session) {
        if (session.getAttribute("newUserRegistration") != null || session.getAttribute("forgotPasswordRequest") != null) {
            return "common/verify-otp";
        } else {
            return "redirect:/login";
        }
    }


    @ResponseBody
    @RequestMapping(value = "/verify-username", method = RequestMethod.POST)
    public boolean isUserRegistered(@RequestParam("email") String email) {
        Users user = this.usersRepository.getUsersByUsernameEquals(email);
        return user != null;
    }
}