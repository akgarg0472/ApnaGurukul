package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.helper.MyConstants;
import com.akgarg.apnagurukul.helper.OTPGenerator;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("users", new Users());
        return "common/register";
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute Users user, BindingResult bindingResult, HttpSession session) {
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
            session.setAttribute("newUserRegistration", user);
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }


    @ResponseBody
    @RequestMapping(value = "/verify-username", method = RequestMethod.POST)
    public boolean isUserRegistered(@RequestParam("email") String email) {
        Users user = this.usersRepository.getUsersByUsernameEquals(email);
        System.out.println(user);
        return user != null;
    }

}
