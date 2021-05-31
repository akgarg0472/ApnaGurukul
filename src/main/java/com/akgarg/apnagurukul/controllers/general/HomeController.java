package com.akgarg.apnagurukul.controllers.general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @ResponseBody
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home() {
        return "This is the home page of the apna gurukul";
    }


    @RequestMapping(value = "/verify-otp", method = RequestMethod.GET)
    public String verifyOtp(HttpSession session) {
        if (session.getAttribute("newUserRegistration") != null) {
            return "common/verify-otp";
        } else {
            return "common/register";
        }
    }
}
