package com.example.demo.repositories;

import com.example.demo.models.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "select u" + " from Student u" + " where u.id = ?1" + " and u.deleted = 0", nativeQuery = true)
    Student findByID(String id);

    @Query(value = "SELECT * FROM Student s WHERE s.id = ?1 AND s.deleted = 0", nativeQuery = true)
    Student findStudent(String id);

    @Query(value = "select u" + " from Student u" + " where u.accountID = ?1" + " and u.deleted = 0",
        nativeQuery = true)
    Student findByAccountUsername(String username);

    @Query(value = "select * from Account where username = ?1 and u.deleted = 0", nativeQuery = true)
    Student findStudentInfor(String username);

    @Query(value = "select *" + " from Student u" + " where u.accountID = ?1" + " and u.deleted = 0",
        nativeQuery = true)
    Student findByAccountID(Long accountID);

    @Query("SELECT s FROM Student s WHERE s.deleted = false")
    List<Student> findByDeletedFalse();

    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO Student (studentCode, phone, fullName, birthday, gender, classID, majorID, accountID, createdAt, updatedAt, deleted, IDCard, year) "
            + "VALUES (:studentCode, :phone, :fullName, :birthday, :gender, :classID, :majorID, :accountID, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, :idcard, YEAR(GETDATE()))",
        nativeQuery = true)
    void saveStudent(@Param("studentCode") String studentCode, @Param("phone") String phone,
        @Param("fullName") String fullName, @Param("birthday") String birthday, @Param("idcard") String idcard,
        @Param("gender") String gender, @Param("classID") Long classID, @Param("majorID") Long majorID,
        @Param("accountID") Long accountID);

    @Query(value = "SELECT rr.studentID, s.id, s.subjectCode, s.name, s.creditsCount, rs.receiptID, rs.id "
        + "FROM ReceiptSubject rs " + "INNER JOIN RegisterReceipt rr ON rr.id = rs.receiptID "
        + "INNER JOIN SubjectSchedule ss ON ss.id = rs.subjectScheduleID "
        + "INNER JOIN Subject s ON ss.subjectID = s.id ", nativeQuery = true)
    List<Object[]> getAllRegistrations();

    @Query(
        value = "SELECT s.* FROM Student s JOIN Account a ON s.accountID = a.id WHERE a.username = ?1 AND a.deleted = 0",
        nativeQuery = true)
    Student findStudentByUserName(String username);

    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO Student (studentCode, phone, fullName, birthday, gender, majorID, accountID, createdAt, updatedAt, deleted, IDCard, year) "
            + "VALUES (:studentCode, :phone, :fullName, :birthday, :gender, :majorID, :accountID, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, :idcard, YEAR(GETDATE()))",
        nativeQuery = true)
    void saveStudent(@Param("studentCode") String studentCode, @Param("phone") String phone,
        @Param("fullName") String fullName, @Param("birthday") String birthday, @Param("idcard") String idcard,
        @Param("gender") String gender, @Param("majorID") Long majorID, @Param("accountID") Long accountID);

    @Query(value = "SELECT *" + " FROM Student" + " WHERE accountID = ?1" + " AND deleted = 0", nativeQuery = true)
    Student findByUsername(Long accountID);

    @Query(value = "select * from Student where accountID = ?1", nativeQuery = true)
    Student findByIDWhologin(Long id);
}
