package com.rena.springsecuritydemo.controller;

import com.rena.springsecuritydemo.dto.AuthenticationRequest;
import com.rena.springsecuritydemo.dto.AuthenticationResponse;
import com.rena.springsecuritydemo.dto.RegistrationRequest;
import com.rena.springsecuritydemo.entity.User;
import com.rena.springsecuritydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String home(){
        return "This is my Home Page";
    }

    @PostMapping("/register")
    public ResponseEntity<User> userRegistration(@RequestBody RegistrationRequest request){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return new ResponseEntity<>(userService.signin(request), HttpStatus.OK);
    }
}
