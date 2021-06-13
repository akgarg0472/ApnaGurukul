package com.akgarg.apnagurukul.controllers.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/student")
public class StudentController {

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "student/dashboard";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String studentLogout() {
        return "redirect:/logout";
    }
}
