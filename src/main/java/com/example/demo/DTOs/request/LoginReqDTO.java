package com.example.demo.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginReqDTO {

    @NotBlank(message = "Trường tên tài khoản không thể thiếu!")
    private String username;

    @NotBlank(message = "Trường mật khẩu không thể thiếu")
    private String password;
}
