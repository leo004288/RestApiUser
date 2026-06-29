package com.example.restapiuser.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(max=100, message = "비밀번호는 100자리 이하입니다")
        String passwd,

        @NotBlank(message = "이름은 필수입력입니다")
        @Size(max=100, message = "이름은 100자리 이하입니다")
        String username,

        @Email(message = "이메일 형식이 틀립니다")
        @Size(max=320, message = "이메일은 320자리 이하입니다")
        String email
) {
}
