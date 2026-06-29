package com.example.restapiuser.service;

import com.example.restapiuser.ApiException.ApiException;
import com.example.restapiuser.Entity.UserEntity;
import com.example.restapiuser.dto.UserCreateRequest;
import com.example.restapiuser.dto.UserResponse;
import com.example.restapiuser.dto.UserUpdateRequest;
import com.example.restapiuser.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private  final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // UserEntity(table) -> UserResponse(화면출력, 비빌번호 빼고)
    public List<UserResponse> findUsers(String keyword) {
        List<UserEntity> users;
        if (keyword == null || keyword.isEmpty()) {
            // 검색어가 없을때
            users = userRepository.findAllByOrderByIndateAsc();
        } else {
            // 검색어가 있을때
            users = userRepository.findByUseridContainingIgnoreCaseOrderByIndateAsc(keyword.trim());
        }
        return users.stream()
                .map(UserResponse::from)  // 모든 요소에 함수를 적용해서 새로운 stream을 만들어라(.map())
                .toList();  // list.stream(0 -> ArrayList로 변경)
    }

    // 회원추가
    @Transactional
    public UserResponse createUser(@Valid UserCreateRequest request) {
        if (userRepository.existsById(request.userid())) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "이미 존재하는 아이디입니다" + request.userid());
        }
        UserEntity user = new UserEntity(
                request.userid(),
                request.passwd(),
                request.username(),
                request.email()
        );
        UserEntity savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    // 회원삭제
    @Transactional
    public void deleteuser(String userid) {
        UserEntity user = getUserEntity(userid);
        userRepository.delete(user);
    }

    // userid로 검색
    private UserEntity getUserEntity(String userid) {
        return userRepository.findById(userid)
                .orElseThrow( () -> new ApiException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다" + userid));
    }

    // 회원정보수정
    @Transactional
    public UserResponse updateuser(String userid, @Valid UserUpdateRequest request) {
        UserEntity user = getUserEntity(userid);
        if (request.passwd() != null && !request.passwd().isBlank() ) {
            user.setPasswd(request.passwd());
        }
        user.setUsername(request.username());
        user.setEmail(request.email());
        return UserResponse.from(userRepository.save(user));
    }

}
