package com.code.AssJava5.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @Id
    @NotBlank(message = "Không được để trống!")
    @Length(min = 5, max = 50, message = "Phải có từ 5 - 50  ký tự!")
    private String username;

//    @NotBlank(message = "Không được để trống!")
    private String password;

    @NotBlank(message = "Không được để trống!")
    private String fullname;

    @NotBlank(message = "Không được để trống!")
    @Email(message = "Email không hợp lệ!")
    private String email;

    private MultipartFile img;

    private Boolean activated;

    private Boolean admin;

}
