package com.akgarg.apnagurukul.repository;

import com.akgarg.apnagurukul.entity.FindTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FindTeacherRepository extends JpaRepository<FindTeacher, Integer> {

    List<FindTeacher> findFindTeachersByCityIgnoreCaseOrStateIgnoreCase(String city, String state);
}
