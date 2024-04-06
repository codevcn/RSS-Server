package com.example.demo.controllers;

import com.example.demo.DTOs.response.AccountResDTO;
import com.example.demo.DTOs.response.StudentResDTO;
import com.example.demo.DTOs.response.StudentResDTO.GetStudentInfoResDTO;
import com.example.demo.DTOs.response.AccountResDTO.GetAccountResDTO;
import com.example.demo.models.Account;
import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import com.example.demo.DTOs.request.CreateStudentRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.StudentRepository;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("get-student-info")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<GetStudentInfoResDTO> getStudentInfo(
        @NonNull HttpServletRequest httpServletRequest
    ) {
        Student student = studentService.getStudentInfo(httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
            new GetStudentInfoResDTO(
                student.getId(),
                student.getStudentCode(),
                student.getPhone(),
                student.getFullName(),
                student.getBirthday(),
                student.getIdcard(),
                student.getGender(),
                student.getMajor(),
                student.getDeleted()
            )
        );
    }

    @GetMapping("get-all-student")
    public ResponseEntity<List<GetStudentInfoResDTO>> getAllStudents() {
        List<Student> students = studentService.getAllActiveStudents();
        List<GetStudentInfoResDTO> studentInfoList = students
            .stream()
            .map(student ->
                new GetStudentInfoResDTO(
                    student.getId(),
                    student.getStudentCode(),
                    student.getPhone(),
                    student.getFullName(),
                    student.getBirthday(),
                    student.getIdcard(),
                    student.getGender(),
                    student.getMajor(),
                    student.getDeleted()
                ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(studentInfoList);
    }

    @GetMapping("all-student")
    public ResponseEntity<List<GetStudentInfoResDTO>> getAllStudentWithAllStatus() {
        List<Student> students = studentService.getAllStudents();
        List<GetStudentInfoResDTO> studentInfoList = students
            .stream()
            .map(student ->
                new GetStudentInfoResDTO(
                    student.getId(),
                    student.getStudentCode(),
                    student.getPhone(),
                    student.getFullName(),
                    student.getBirthday(),
                    student.getIdcard(),
                    student.getGender(),
                    student.getMajor(),
                    student.getDeleted()
                ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(studentInfoList);
    }

    @GetMapping("get-student/{id}")
    public ResponseEntity<GetStudentInfoResDTO> getSelectedStudentInfo(
        @PathVariable("id") Long id
    ) {
        Student student = studentService.getStudentById(id);
        GetStudentInfoResDTO studentInfoDTO = new GetStudentInfoResDTO(
            student.getId(),
            student.getStudentCode(),
            student.getPhone(),
            student.getFullName(),
            student.getBirthday(),
            student.getIdcard(),
            student.getGender(),
            student.getMajor(),
            student.getDeleted()
        );
        return ResponseEntity.ok(studentInfoDTO);
    }

    @PutMapping("update-student/{id}")
    public ResponseEntity<GetStudentInfoResDTO> updateStudent(
        @PathVariable("id") Long id,
        @RequestBody StudentResDTO.GetStudentInfoResDTO updatedStudentInfo
    ) {
        Student updatedStudent = studentService.updateStudent(id, updatedStudentInfo);
        if (updatedStudent != null) {
            GetStudentInfoResDTO updatedStudentResponse = new GetStudentInfoResDTO(
                updatedStudent.getId(),
                updatedStudent.getStudentCode(),
                updatedStudent.getFullName(),
                updatedStudent.getGender(),
                updatedStudent.getBirthday(),
                updatedStudent.getIdcard(),
                updatedStudent.getPhone(),
                updatedStudent.getMajor(),
                updatedStudent.getDeleted()
            );
            return ResponseEntity.ok(updatedStudentResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/hide-student/{id}")
    public ResponseEntity<String> hideStudent(@PathVariable("id") Long id) {
        studentService.hiddenStudent(id); 
        return ResponseEntity.ok().build(); 
    }

    @PostMapping("/create-student")
    @Transactional
    public ResponseEntity<String> createStudent(@RequestBody CreateStudentRequest request) {
        System.out.println("Thông tin sinh viên mới:");
        System.out.println("Student Code: " + request.getStudentCode());
        System.out.println("Full Name: " + request.getFullName());
        System.out.println("Gender: " + request.getGender());
        System.out.println("Birthday: " + request.getBirthday());
        System.out.println("ID card: " + request.getIdcard());
        System.out.println("Phone: " + request.getPhone());
        System.out.println("Major ID: " + request.getMajor());

        System.out.println("Thông tin tài khoản:");
        System.out.println("Username: " + request.getUsername());
        System.out.println("Password: " + request.getPassword());

        //Thêm tài khoản
        String username = request.getUsername();
        String password = request.getPassword();
        
        
        accountRepository.saveAccount(username, password);
        Long accountId = accountRepository.findAccountIdByUsername(username);
        System.out.println("accountId: " + accountId);

        //Thêm sinh viên
        String studentCode = request.getStudentCode();
        String phone = request.getPhone();
        String fullName = request.getFullName();
        String birthday = request.getBirthday(); 
        String idcard = request.getIdcard(); 
        String gender = request.getGender();
        Long majorID = request.getMajor().getId();
        studentRepository.saveStudent(studentCode, phone, fullName, birthday, idcard, gender, majorID, accountId);
        System.out.println("Thành công!!!");
        

        return ResponseEntity.ok("Student created successfully!");
    }

}
