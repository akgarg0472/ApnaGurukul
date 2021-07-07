package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.helper.OTPGenerator;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

    private final OTPGenerator otpGenerator;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ForgotPasswordController(OTPGenerator otpGenerator,
                                    UsersRepository usersRepository,
                                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.otpGenerator = otpGenerator;
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @RequestMapping(value = "process-fp", method = RequestMethod.POST)
    public String processOtpVerification(HttpSession session,
                                         @RequestParam("email") String email) {
        if (session.getAttribute("newUserRegistration") != null) {
            session.removeAttribute("newUserRegistration");
        }

        if (email != null && !email.equals("")) {
            Users user = this.usersRepository.getUserByUsername(email);

            if (user != null) {
                session.setAttribute("fpEmail", email);
                session.setAttribute("forgotPasswordRequest", "fpr");

                if (this.otpGenerator.getOtp(email) == 0) {
                    session.setAttribute("fpEmail", email);
                    session.setAttribute("forgotPasswordRequest", "fpr");
                    int generatedOTP = this.otpGenerator.generateOTP(email);
                    EmailSender.sendEmail(email, "Forgot Password OTP", EmailMessages.forgotPasswordOTPMessage(email, user.getName(), String.valueOf(generatedOTP)));
                }
                return "redirect:/verify-otp";
            }
        }

        // todo later fix
        return "server-error";
    }


    @ResponseBody
    @RequestMapping(value = "verify-fp-otp", method = RequestMethod.POST)
    public String processOtpVerification(HttpSession session,
                                         @RequestParam("otp") int otp) {
        String fpEmail = (String) session.getAttribute("fpEmail");

        if (fpEmail != null && !fpEmail.equals("")) {
            int generatedOtp = this.otpGenerator.getOtp(fpEmail);

            if (generatedOtp == 0) {
                return "OTP_EXPIRED";
            }

            if (generatedOtp == otp) {
                this.otpGenerator.deleteOTP(fpEmail);
                return "FP_OTP_VERIFIED";
            } else {
                return "OTP_MISMATCHED";
            }
        }
        return "UNAUTHORIZED_ACCESS";
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

        Users user = this.usersRepository.getUserByUsername(email);
        String passwordMatcher = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        if (user != null) {
            if (newPassword == null || newPassword.equals("") || !newPassword.matches(passwordMatcher)) {
                session.setAttribute("npError", "np-error");
                return "redirect:/new-password";
            }

            if (confirmNewPassword == null || confirmNewPassword.equals("") || !confirmNewPassword.matches(passwordMatcher)) {
                session.setAttribute("cnpError", "cnp-error");
                return "redirect:/new-password";
            }

            if (!newPassword.equals(confirmNewPassword)) {
                session.setAttribute("npmError", "npmError");
                return "redirect:/new-password";
            }

            user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
            this.usersRepository.save(user);

            session.removeAttribute("fpEmail");
            session.removeAttribute("forgotPasswordRequest");
            session.setAttribute("passwordChangedSuccessfully", "passwordChangedSuccessfully");
            EmailSender.sendEmail(user.getUsername(), "Password successfully updated", EmailMessages.passwordSuccessfullyChangedMessage(user.getUsername(), user.getName()));
        }

        return "redirect:/login";
    }
}