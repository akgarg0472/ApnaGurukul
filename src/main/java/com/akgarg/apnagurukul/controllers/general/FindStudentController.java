package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.FindStudent;
import com.akgarg.apnagurukul.service.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class FindStudentController {

    private final FindStudentService findStudentService;

    @Autowired
    public FindStudentController(FindStudentService findStudentService) {
        this.findStudentService = findStudentService;
    }


    @RequestMapping(value = "/find-student", method = RequestMethod.GET)
    public String findTeacher(Model model) {
        // adding dummy data for debugging purpose
        List<FindStudent> students = this.findStudentService.find();
        model.addAttribute("students", students);

        return "/common/find-student";
    }


    @RequestMapping(value = "/find-students/{city}/{state}", method = RequestMethod.GET)
    public String findTeacherList(@PathVariable("city") String city,
                                  @PathVariable("state") String state,
                                  Model model) {
        List<FindStudent> students = this.findStudentService.find(city, state);
        model.addAttribute("students", students);

        return "/common/find-student";
    }
}
