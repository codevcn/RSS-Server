package com.example.demo.DTOs.response;

//import com.example.demo.DTOs.response.StudentResDTOs.MajorOfSubjectResDTO;

public class MajorResDTOs {

    public static record GetMajorInfoResDTO(
        String id,
        String name
        // Date updatedAt,
        // Long creditCount,
        // MajorOfSubjectResDTO major
    ) {}
}