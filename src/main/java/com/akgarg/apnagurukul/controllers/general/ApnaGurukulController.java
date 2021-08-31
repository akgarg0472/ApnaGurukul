package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.ContactUs;
import com.akgarg.apnagurukul.entity.SellBookAd;
import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.repository.ContactUsRepository;
import com.akgarg.apnagurukul.repository.SellBookAdRepository;
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
import java.util.List;

@Controller
public class ApnaGurukulController {

    private final UsersRepository usersRepository;
    private final ContactUsRepository contactUsRepository;
    private final SellBookAdRepository sellBookAdRepository;

    @Autowired
    public ApnaGurukulController(UsersRepository usersRepository,
                                 ContactUsRepository contactUsRepository,
                                 SellBookAdRepository sellBookAdRepository) {
        this.usersRepository = usersRepository;
        this.contactUsRepository = contactUsRepository;
        this.sellBookAdRepository = sellBookAdRepository;
    }


    @SuppressWarnings("CommentedOutCode")
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        int totalusers = 154868;
        int totalBooks = 3636485;

        // todo later
        // int totalusers = this.usersRepository.findAll().size();
        // int totalBooks = this.bookRepository.findAll().size();

        model.addAttribute("usersCount", totalusers);
        model.addAttribute("totalBooks", totalBooks);
        return "common/home";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal,
                        HttpSession session,
                        Model model) {
        if (principal != null) {
            Users users = this.usersRepository.getUserByUsername(principal.getName());

            if (users != null) {
                String role = users.getRole();

                switch (role) {
                    case "ROLE_ADMIN":
                        return "redirect:/admin/dashboard";

                    case "ROLE_USER":
                        return "redirect:/user/dashboard";
                }
            }
        }
        if (session.getAttribute("logoutSuccess") != null) {
            session.removeAttribute("logoutSuccess");
            model.addAttribute("logoutSuccess", true);
        }

        return "common/login";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String register(Principal principal,
                           Model model) {
        if (principal != null) {
            Users users = this.usersRepository.getUserByUsername(principal.getName());

            if (users != null) {
                String role = users.getRole();

                switch (role) {
                    case "ROLE_ADMIN":
                        return "redirect:/admin/dashboard";

                    case "ROLE_USER":
                        return "redirect:/user/dashboard";
                }
            }
        }

        model.addAttribute("users", new Users());
        return "common/register";
    }


    @RequestMapping(value = "/sell-book", method = RequestMethod.GET)
    public String sellBook(Principal principal,
                           HttpSession session,
                           Model model) {
        if (principal == null) {
            session.setAttribute("sellBookLogin", true);
            model.addAttribute("userNotLogin", "userNotLogin");
        }
        return "common/sell-book";
    }


    @RequestMapping(value = "/buy-book", method = RequestMethod.GET)
    public String buyBook(Principal principal,
                          HttpSession session,
                          Model model) {
        if (principal == null) {
            session.setAttribute("buyBookLogin", true);
            model.addAttribute("userNotLogin", "userNotLogin");
        } else {
            Users loggedInUser = this.usersRepository.getUserByUsername(principal.getName());
            model.addAttribute("user", loggedInUser);
            List<SellBookAd> books = this.sellBookAdRepository.findAll();
            model.addAttribute("books", books);
        }
        return "common/buy-book";
    }


    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String forgotPassword() {
        return "common/forgot-password";
    }


    @RequestMapping(value = "/about-us", method = RequestMethod.GET)
    public String aboutUs() {
        return "common/about-us";
    }


    @RequestMapping(value = "/contact-us", method = RequestMethod.GET)
    public String contactUs() {
        return "common/contact-us";
    }


    @RequestMapping(value = "/logout-success", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        if (session.getAttribute("sellBookLogin") != null) {
            session.removeAttribute("sellBookLogin");
        }
        if (session.getAttribute("buyBookLogin") != null) {
            session.removeAttribute("buyBookLogin");
        }

        session.setAttribute("logoutSuccess", true);
        return "redirect:/login";
    }


    @RequestMapping(value = "/verify-otp", method = RequestMethod.GET)
    public String verifyOtp(HttpSession session) {
        if (session.getAttribute("newUserRegistration") != null ||
                session.getAttribute("forgotPasswordRequest") != null) {
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


    @ResponseBody
    @RequestMapping(value = "/cont-us-query", method = RequestMethod.POST)
    public boolean contactUsQuery(@RequestParam("fname") String firstName,
                                  @RequestParam("lname") String lastName,
                                  @RequestParam("email") String email,
                                  @RequestParam("desc") String description) {
        if (lastName != null
                && !lastName.trim().equals("")
                && email != null
                && !email.trim().equals("")
                && description != null
                && !description.trim().equals("")) {
            ContactUs contactUs = new ContactUs(firstName, lastName, email, description);
            this.contactUsRepository.save(contactUs);

            EmailSender.sendEmail("akgarg0472@gmail.com", "New contact us query",
                    EmailMessages.contactQueryMessage(contactUs));
            EmailSender.sendEmail(contactUs.getEmail(), "Thanks for contacting ApnaGurukul",
                    EmailMessages.contactConfirmMessage(contactUs.getFirstName() + " " + contactUs.getLastName()));

            return true;
        } else {
            return false;
        }
    }
}