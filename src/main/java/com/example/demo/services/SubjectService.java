package com.example.demo.services;

import com.example.demo.DTOs.response.SubjectResDTO;
import com.example.demo.models.Subject;
import com.example.demo.repositories.SubjectRepository;
import com.example.demo.utils.exceptions.CustomBaseException;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject getSubjectInfo(@NonNull HttpServletRequest httpServletRequest, String id)
        throws CustomBaseException {
        Subject subject = subjectRepository.findSubject(id);
        if (subject != null) {
            return subject;
        } else {
            throw new CustomBaseException("Không tìm thấy môn học");
        }
    }

    public Subject getSubjectById(String ID) {
        return subjectRepository.findSubjectbyid(ID);
    }

    public Subject createSubject(SubjectResDTO.CreateSubjectInfoResDTO CreateSubjectInfo) {
        Subject subject = new Subject();
        subject.setName(CreateSubjectInfo.name());
        subject.setCreditsCount(CreateSubjectInfo.creditCount());
        subject.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        subject.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        subject.setMajor(CreateSubjectInfo.major());
        subject.setDeleted(false);
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> allSubjects = subjectRepository.findAll();
        List<Subject> ListAllSubject = new ArrayList<>();

        for (Subject subject : allSubjects) {
            if (subject.getDeleted() == false) {
                ListAllSubject.add(subject);
            }
        }

        return ListAllSubject;
    }

    public Subject hideSubject(String id) {
        Subject subject = subjectRepository.findSubject(id);
        subject.setDeleted(true);
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(String id, SubjectResDTO.GetSubjectResDTO updatedSubjectInfo) {
        Subject subject = subjectRepository.findSubjectbyid(id);
        subject.setName(updatedSubjectInfo.name());
        subject.setCreditsCount(updatedSubjectInfo.creditCount());
        subject.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        subject.setMajor(updatedSubjectInfo.major());
        return subjectRepository.save(subject);
    }
}
