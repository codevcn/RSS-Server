package com.example.demo.DTOs.response;

import java.sql.Date;

public class StudentResDTO {

    public static record GetStudentInfoResDTO(
        Long id,
        String studentCode,
        String phone,
        String fullName,
        Date birthday,
        String gender,
        String major
        //String accountUsername,
        //String role
    ) {}
    // public static record MajorOfSubjectResDTO(
    //     String majorID,
    //     String majorName
    // ) {}

}
