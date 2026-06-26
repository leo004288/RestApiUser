package com.example.restapiuser.controller;

import com.example.restapiuser.dto.UserResponse;
import com.example.restapiuser.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // @Controller + ResponseBody
@CrossOrigin(origins = "http://localhost:63342") // 8080 접근허용
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // GET http://localhost:8080/api/users
    // GET http://localhost:8080/api/users?keyword=user
    @GetMapping
    public List<UserResponse> list(
            @RequestParam(required = false) String keyword) {

        return userService.findUsers(keyword);
    }

}
