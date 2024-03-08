package com.example.demo.services;

import com.example.demo.DTOs.response.AdminResDTOs;
import com.example.demo.models.Admin;
import com.example.demo.repositories.AdminRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminAccountInfo(@NonNull HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getUserPrincipal().getName();
        return adminRepository.findByAccountUsername(username);
    }
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return (List<Admin>) adminRepository.findAll();
    }

    public Admin getAdminById(String accountID) {
        return adminRepository.findByAccountUsername(accountID);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // public Admin updateAdmin(String id, AdminResDTOs.GetAdminInfoResDTO
    // updatedAdminInfo) {
    // Admin admin = new Admin();
    // admin.setIdcard(updatedAdminInfo.idcard());
    // admin.setFullName(updatedAdminInfo.fullName());
    // admin.setBirthday(updatedAdminInfo.birthday());
    // admin.setGender(updatedAdminInfo.gender());

    // return adminRepository.save(admin);
    // }
    public Admin updateAdmin(Integer id, AdminResDTOs.GetAdminInfoResDTO updatedAdminInfo) {
        Admin admin = adminRepository.findById(id).orElse(null);
        admin.setIdcard(updatedAdminInfo.idcard());
        admin.setFullName(updatedAdminInfo.fullName());
        admin.setBirthday(updatedAdminInfo.birthday());
        admin.setGender(updatedAdminInfo.gender());
        return adminRepository.save(admin);

    }

    // public void deleteAdmin(String id) {
    //     adminRepository.deleteById(id);
    // }
}