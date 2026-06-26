package com.example.restapiuser.controller;

import com.example.restapiuser.dto.UserCreateRequest;
import com.example.restapiuser.dto.UserResponse;
import com.example.restapiuser.service.UserService;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController  // @Controller + ResponseBody
@CrossOrigin(origins = "http://localhost:63342") // 8080 접근허용
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final HandlerMapping resourceHandlerMapping;

    public UserRestController(UserService userService, @Nullable HandlerMapping resourceHandlerMapping) {
        this.userService = userService;
        this.resourceHandlerMapping = resourceHandlerMapping;
    }

    // GET http://localhost:8080/api/users
    // GET http://localhost:8080/api/users?keyword=user
    @GetMapping
    public List<UserResponse> list(
            @RequestParam(required = false) String keyword) {
        return userService.findUsers(keyword);
    }

    // POST http://localhost:8080/api/users  -  회원가입
    // @RequestBody : 넘어오는 파라미터는 json이다
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(response.userid())  // recode는 getUserid() -> response.userid()
                .toUri();
        return ResponseEntity.created(location).body(response);
        // ResponseEntity : 저장된 data와 location, 상태코드를 반환해줌
        // 201 : insert 성공
        // .created(location) : 생략가능
        // .body(response) : 생성된 사용자 정보를 json으로 응답
    }

}
