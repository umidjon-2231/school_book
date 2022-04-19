package com.project.school_book.controller;


import com.project.school_book.dto.ApiResponse;
import com.project.school_book.dto.LoginDto;
import com.project.school_book.entity.User;
import com.project.school_book.entity.enums.Role;
import com.project.school_book.repository.UserRepository;
import com.project.school_book.security.JwtProvider;
import com.project.school_book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    final UserRepository userRepository;
    final UserService userService;
    final JwtProvider jwtProvider;
    final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse res) {
        Optional<User> byUsername = userRepository.findByUsername(loginDto.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (byUsername.isEmpty() || !byUsername.get().isEnabled() || !passwordEncoder.matches(loginDto.getPassword(), byUsername.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
        String token = jwtProvider.generateToken(loginDto.getUsername());
        Cookie cookie = new Cookie("token", token);
        res.addCookie(cookie);
        return ResponseEntity.ok().body(token);
    }
    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody LoginDto loginDto, HttpServletResponse res){
        Optional<User> optionalUser = userRepository.findByUsername(loginDto.getUsername());
        if(optionalUser.isPresent()){
            return ResponseEntity.status(400).body(ApiResponse.builder()
                            .message("This username already exist!")
                    .build());
        }
        userRepository.save(User.builder()
                        .username(loginDto.getUsername())
                        .password(passwordEncoder.encode(loginDto.getPassword()))
                        .role(Role.USER)
                .build());
        String token = jwtProvider.generateToken(loginDto.getUsername());
        Cookie cookie = new Cookie("token", token);
        res.addCookie(cookie);
        return ResponseEntity.ok().body(ApiResponse.builder()
                        .message("Success registered")
                        .data(token)
                        .success(true)
                .build());
    }


}
