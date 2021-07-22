package com.akgarg.apnagurukul.service;


import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.helper.OTPGenerator;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class ForgotPasswordService {

    private final OTPGenerator otpGenerator;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public ForgotPasswordService(OTPGenerator otpGenerator,
                                 UsersRepository usersRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.otpGenerator = otpGenerator;
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String processOtpVerification(HttpSession session,
                                         String email) {
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


    public String processOtpVerification(HttpSession session,
                                         int otp) {
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


    public String processNewPassword(HttpSession session,
                                     String newPassword,
                                     String confirmNewPassword) {
        String email = (String) session.getAttribute("fpEmail");
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
