package com.akgarg.apnagurukul.service;

import com.akgarg.apnagurukul.entity.FindStudent;
import com.akgarg.apnagurukul.repository.FindStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindStudentService {

    private final FindStudentRepository findStudentRepository;

    @Autowired
    public FindStudentService(FindStudentRepository findStudentRepository) {
        this.findStudentRepository = findStudentRepository;
    }

    public List<FindStudent> find() {
        return this.findStudentRepository.findAll();
    }

    public List<FindStudent> find(String city, String state) {
        return this.findStudentRepository.findFindStudentByCityIgnoreCaseOrStateIgnoreCase(city, state);
    }
}
