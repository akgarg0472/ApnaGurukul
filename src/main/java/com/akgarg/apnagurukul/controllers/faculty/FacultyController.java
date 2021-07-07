package com.akgarg.apnagurukul.controllers.faculty;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @ResponseBody
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpSession session) {
        if (session.getAttribute("buyBookLogin") != null) {
            session.removeAttribute("buyBookLogin");
            return "redirect:/buy-book";
        } else if (session.getAttribute("sellBookLogin") != null) {
            session.removeAttribute("sellBookLogin");
            return "redirect:/sell-book";
        }
        
        return "This is the faculty dashboard";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String facultyLogout() {
        System.out.println("faculty logout called");
        return "redirect:/logout";
    }
}
