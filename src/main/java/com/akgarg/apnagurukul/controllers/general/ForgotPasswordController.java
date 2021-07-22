package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @Autowired
    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }


    @RequestMapping(value = "process-fp", method = RequestMethod.POST)
    public String processOtpVerification(HttpSession session,
                                         @RequestParam("email") String email) {
        return this.forgotPasswordService.processOtpVerification(session, email);
    }


    @ResponseBody
    @RequestMapping(value = "verify-fp-otp", method = RequestMethod.POST)
    public String processOtpVerification(HttpSession session,
                                         @RequestParam("otp") int otp) {
        return this.forgotPasswordService.processOtpVerification(session, otp);
    }


    @RequestMapping(value = "/new-password", method = RequestMethod.GET)
    public String newPassword(HttpSession session) {
        String fpEmail = (String) session.getAttribute("fpEmail");
        String forgotPasswordRequest = (String) session.getAttribute("forgotPasswordRequest");

        if (fpEmail != null && forgotPasswordRequest != null) {
            return "common/new-password";
        }

        return "redirect:/login";
    }


    @RequestMapping(value = "/process-new-password", method = RequestMethod.POST)
    public String processNewPassword(HttpSession session,
                                     @RequestParam("new-pass") String newPassword,
                                     @RequestParam("conf-new-pass") String confirmNewPassword) {
        String email = (String) session.getAttribute("fpEmail");
        if (email == null) {
            return "redirect:/login";
        }

        return this.forgotPasswordService.processNewPassword(session, newPassword, confirmNewPassword);
    }
}