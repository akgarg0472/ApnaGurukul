package com.akgarg.apnagurukul.repository;

import com.akgarg.apnagurukul.entity.FindStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FindStudentRepository extends JpaRepository<FindStudent, String> {

    List<FindStudent> findFindStudentByCityIgnoreCaseOrStateIgnoreCase(String city, String state);
}
