package com.akgarg.apnagurukul.service;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.helper.*;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserRegistrationService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OTPGenerator otpGenerator;


    @Autowired
    public UserRegistrationService(UsersRepository usersRepository,
                                   BCryptPasswordEncoder bCryptPasswordEncoder,
                                   OTPGenerator otpGenerator) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.otpGenerator = otpGenerator;
    }


    public String processRegistrationInformation(Users user,
                                                 HttpSession session) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfilePicture(MyConstants.DEFAULT_PROFILE_PICTURE);
        user.setRole("ROLE_USER");

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


    public String processOtpVerification(HttpSession session,
                                         int otp) {
        Users user = (Users) session.getAttribute("newUserRegistration");

        if (user != null) {
            System.out.println(user.getRole());
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


    public boolean resendRegistrationVerificationOtp(Users user) {
        if (user != null) {
            this.otpGenerator.deleteOTP(user.getUsername());
            int otp = this.otpGenerator.generateOTP(user.getUsername());
            return EmailSender.sendEmail(user.getUsername(), "OTP for registration confirmation",
                    EmailMessages.registrationOTPMessage(user.getUsername(), user.getName(), otp));
        }

        return false;
    }
}