package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.repository.UsersRepository;
import com.akgarg.apnagurukul.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;
    private final UsersRepository usersRepository;


    @Autowired
    public RegistrationController(UserRegistrationService userRegistrationService,
                                  UsersRepository usersRepository) {
        this.userRegistrationService = userRegistrationService;
        this.usersRepository = usersRepository;
    }


    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute Users user,
                                  BindingResult bindingResult,
                                  HttpSession session) {
        if (user == null) {
            return "null";
        }

        boolean isBindingResultHasErrors = false;

        if (bindingResult.hasErrors()) {
            isBindingResultHasErrors = true;
            bindingResult.getAllErrors().forEach(error -> {
                String errorName = error.getDefaultMessage() != null ?
                        error.getDefaultMessage().split(" ")[0] : "null";
                String errorMessage = error.getDefaultMessage();
                session.setAttribute(errorName + "_Error", errorMessage);
            });
        }

        if (isBindingResultHasErrors) {
            return "ERRORS";
        }

        return this.userRegistrationService.processRegistrationInformation(user, session);
    }


    @ResponseBody
    @RequestMapping(value = "process-otp-verification", method = RequestMethod.POST)
    public String processOtpVerification(HttpSession session,
                                         @RequestParam("otp") int otp) {
        return this.userRegistrationService.processOtpVerification(session, otp);
    }


    @ResponseBody
    @RequestMapping(value = "/resend-otp", method = RequestMethod.POST)
    public boolean resendOTP(HttpSession session) {
        Users user = null;

        if (session.getAttribute("newUserRegistration") != null) {
            user = (Users) session.getAttribute("newUserRegistration");
        } else if (session.getAttribute("forgotPasswordRequest") != null) {
            user = this.usersRepository.getUserByUsername((String) session.getAttribute("fpEmail"));
        }

        return this.userRegistrationService.resendRegistrationVerificationOtp(user);
    }
}