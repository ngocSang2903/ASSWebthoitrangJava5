package com.code.AssJava5.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassDto {

    @NotBlank(message = "Không được để trống!")
    private String password;

    @NotBlank(message = "Không được để trống!")
    private String passwordnew;

    @NotBlank(message = "Không được để trống!")
    private String confirmpassword;
}
