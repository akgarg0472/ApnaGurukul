package com.akgarg.apnagurukul.repository;

import com.akgarg.apnagurukul.entity.FindTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FindTeacherRepository extends JpaRepository<FindTeacher, Integer> {

    List<FindTeacher> findFindTeachersByCityAndState(String city, String state);
}
