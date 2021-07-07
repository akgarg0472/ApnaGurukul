package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.helper.*;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class RegistrationController {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OTPGenerator otpGenerator;

    @Autowired
    public RegistrationController(UsersRepository usersRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  OTPGenerator otpGenerator) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.otpGenerator = otpGenerator;
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
        boolean isRoleInvalid = false;

        if (bindingResult.hasErrors()) {
            isBindingResultHasErrors = true;
            bindingResult.getAllErrors().forEach(error -> {
                String errorName = error.getDefaultMessage() != null ? error.getDefaultMessage().split(" ")[0] : "null";
                String errorMessage = error.getDefaultMessage();
                session.setAttribute(errorName + "_Error", errorMessage);
            });
        }

        if (user.getRole().equals("default")) {
            session.setAttribute("Role_Error", "Please select your role");
            isRoleInvalid = true;
        }

        if (isBindingResultHasErrors || isRoleInvalid) {
            return "ERRORS";
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfilePicture(MyConstants.DEFAULT_PROFILE_PICTURE);

        if (user.getRole().equals("faculty")) {
            user.setRole("ROLE_FACULTY");
        } else if (user.getRole().equals("student")) {
            user.setRole("ROLE_STUDENT");
        }

        int otp = otpGenerator.generateOTP(user.getUsername());
        boolean sendEmail = EmailSender.sendEmail(user.getUsername(), "Registration OTP", EmailMessages.registrationOTPMessage(user.getUsername(), user.getName(), otp));

        if (sendEmail) {
            if (session.getAttribute("forgotPasswordRequest") != null) {
                session.removeAttribute("forgotPasswordRequest");
            }

            if (session.getAttribute("fpEmail") != null) {
                session.removeAttribute("fpEmail");
            }

            session.setAttribute("newUserRegistration", user);
            return "SUCCESS";
        } else {
            this.otpGenerator.deleteOTP(user.getUsername());
            return "FAIL";
        }
    }


    @ResponseBody
    @RequestMapping(value = "process-otp-verification", method = RequestMethod.POST)
    public String processOtpVerification(HttpSession session,
                                         @RequestParam("otp") int otp) {
        Users user = (Users) session.getAttribute("newUserRegistration");

        if (user != null) {
            int generatedOtp = this.otpGenerator.getOtp(user.getUsername());

            if (generatedOtp == 0) {
                return "OTP_EXPIRED";
            }

            if (generatedOtp == otp) {
                session.removeAttribute("newUserRegistration");
                this.otpGenerator.deleteOTP(user.getUsername());
                user.setJoinDate(DateAndTimeMethods.getCurrentDate());
                this.usersRepository.save(user);
                EmailSender.sendEmail(user.getUsername(), "Registration successful", EmailMessages.registrationSuccessMessage(user.getUsername(), user.getName()));
                session.setAttribute("registrationSuccessful", "Registration successful");
                return "REGISTRATION_SUCCESSFUL";
            } else {
                return "OTP_MISMATCHED";
            }
        }
        return "UNAUTHORIZED_ACCESS";
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

        if (user != null) {
            this.otpGenerator.deleteOTP(user.getUsername());
            int otp = this.otpGenerator.generateOTP(user.getUsername());
            return EmailSender.sendEmail(user.getUsername(), "OTP for registration confirmation", EmailMessages.registrationOTPMessage(user.getUsername(), user.getName(), otp));
        }

        return false;
    }
}