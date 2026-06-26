package com.example.restapiuser.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// record 생성자를 만들어준다
// getUserid() -> userid() 를 만든다 : getter 역할, setter는 X
public record UserCreateRequest (
        @NotBlank(message = "아이디는 필수입니다")
        @Size(max = 50, message = "아이디는 최대 50자 입니다")
        String userid,

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(max = 100, message = "비빌번호는 최대 100자 입니다")
        String passwd,

        @NotBlank(message = "이름은 필수입니다")
        @Size(max = 100, message = "이름은 최대 100자 입니다")
        String username,

        @Email(message = "이메일 형식이 올바르지 않습니다")
        @Size(max = 320, message = "이메일은 최대 320자 입니다")
        String email
) {

}
