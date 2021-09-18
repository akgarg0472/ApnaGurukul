package com.akgarg.apnagurukul.controllers.general;

import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FindTeacherController {

    private final UsersRepository usersRepository;

    @Autowired
    public FindTeacherController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @RequestMapping(value = "/find-teacher", method = RequestMethod.GET)
    public String findTeacher() {
        return "/common/find-teacher";
    }
}
