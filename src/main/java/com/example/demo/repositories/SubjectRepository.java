package com.example.demo.repositories;

import com.example.demo.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query(value = "SELECT * FROM Subject s WHERE s.id = ?1 OR s.name LIKE N'%?1%' OR s.majorID = ?1",
        nativeQuery = true)
    Subject findSubject(String input);

    // s WHERE s.id LIKE %?1% OR s.name LIKE %?1% OR s.majorID LIKE %?1%
    @Query(value = "SELECT * FROM Subject WHERE id = ?1 ", nativeQuery = true)
    Subject findSubjectbyid(Long id);

    @Query(value = "SELECT * FROM Subject WHERE majorID = ?1", nativeQuery = true)
    List<Subject> findByMajorID(Long majorID);
}
