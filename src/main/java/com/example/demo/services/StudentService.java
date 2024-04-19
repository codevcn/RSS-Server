package com.example.demo.services;

import com.example.demo.DTOs.response.StudentResDTO;
import com.example.demo.models.Account;
import com.example.demo.models.Major;
import com.example.demo.models.Student;
import com.example.demo.models.ReceiptSubject;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.ReceiptSubjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ReceiptSubjectRepository receiptSubjectRepository;

    public Student getStudentInfo(@NonNull HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getUserPrincipal().getName();
        return studentRepository.findByAccountUsername(username);
    }

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    public List<Student> getAllActiveStudents() {
        return studentRepository.findByDeletedFalse();
    }

    public Student updateStudent(Long id, StudentResDTO.GetStudentInfoResDTO updatedStudentInfo) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setStudentCode(updatedStudentInfo.studentCode());
            student.setFullName(updatedStudentInfo.fullName());
            student.setGender(updatedStudentInfo.gender());
            student.setBirthday(updatedStudentInfo.birthday());
            student.setIdcard(updatedStudentInfo.idcard());
            student.setPhone(updatedStudentInfo.phone());
            student.setMajor(updatedStudentInfo.major());
            return studentRepository.save(student);
        } else {
            return null;
        }
    }

    public Student hideStudent(String id) {
        Student student = studentRepository.findStudent(id);
        student.setDeleted(true);
        return studentRepository.save(student);
    }

//     public ReceiptSubject deleteCourse(Long id) {
//         ReceiptSubject course = receiptSubjectRepository.findCourse(id);
//         System.out.println(">>>>>>>" + id + ">>>>>>>");
//         System.out.println(">>>>>>>" + course + ">>>>>>>");
// //         List<Long> courseIds = receiptSubjectRepository.getAllCourseIds();
// // for (Long courseId : courseIds) {
// //     System.out.println("ID của course: " + courseId);
// // }
//         receiptSubjectRepository.deleteCourseById(id);
//         return receiptSubjectRepository.save(course);
//     }

    @Transactional
    public void deleteCourse(Long id) {
        ReceiptSubject course = receiptSubjectRepository.findCourse(id);
        System.out.println(">>>>>>>" + id + ">>>>>>>");
        System.out.println(">>>>>>>" + course + ">>>>>>>");
        receiptSubjectRepository.deleteCourseById(id);
    }
    

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student findStudentByAccountID(Long id) {
        return studentRepository.findByAccountID(id);
    }

    public List<Object[]> getAllRegistrations() {
        return studentRepository.getAllRegistrations();
    }

}
