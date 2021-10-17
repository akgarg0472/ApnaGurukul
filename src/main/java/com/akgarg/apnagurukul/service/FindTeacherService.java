package com.akgarg.apnagurukul.service;

import com.akgarg.apnagurukul.entity.FindTeacher;
import com.akgarg.apnagurukul.repository.FindTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindTeacherService {

    private final FindTeacherRepository findTeacherRepository;

    @Autowired
    public FindTeacherService(FindTeacherRepository findTeacherRepository) {
        this.findTeacherRepository = findTeacherRepository;
    }

    public List<FindTeacher> find(String city, String state) {
        return this.findTeacherRepository.findFindTeachersByCityAndState(city, state);
    }
}
