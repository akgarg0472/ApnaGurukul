package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.entity.FindTeacher;
import com.akgarg.apnagurukul.service.FindTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FindTeacherController {

    private final FindTeacherService findTeacherService;

    @Autowired
    public FindTeacherController(FindTeacherService findTeacherService) {
        this.findTeacherService = findTeacherService;

    }

    @RequestMapping(value = "/find-teacher", method = RequestMethod.GET)
    public String findTeacher(Model model) {
        // adding dummy data for debugging purpose
        List<FindTeacher> teachers = new ArrayList<>();
        FindTeacher teacher = new FindTeacher();
        teacher.setName("name");
        teacher.setSubject("subject");
        teacher.setEmail("email@email.email");
        teacher.setExperience(1.25);
        FindTeacher teacher1 = new FindTeacher();
        teacher1.setName("name");
        teacher1.setSubject("subject");
        teacher1.setExperience(1.25);
        teacher1.setEmail("email@email.email");

        teachers.add(teacher);
        teachers.add(teacher1);
        model.addAttribute("teachers", teachers);

        return "/common/find-teacher";
    }


    @RequestMapping(value = "/find-teachers/{city}/{state}", method = RequestMethod.GET)
    public String findTeacherList(@PathVariable("city") String city,
                                  @PathVariable("state") String state,
                                  Model model) {
        List<FindTeacher> teachers = this.findTeacherService.find(city, state);
        if (teachers != null) {
            model.addAttribute("teachers", teachers);
        }

        return "/common/find-teacher";
    }
}
