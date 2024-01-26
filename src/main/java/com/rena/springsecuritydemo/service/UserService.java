package com.rena.springsecuritydemo.service;

import com.rena.springsecuritydemo.dto.AuthenticationRequest;
import com.rena.springsecuritydemo.dto.AuthenticationResponse;
import com.rena.springsecuritydemo.dto.RegistrationRequest;
import com.rena.springsecuritydemo.entity.User;
import com.rena.springsecuritydemo.enums.Role;
import com.rena.springsecuritydemo.repository.UserRepository;
import com.rena.springsecuritydemo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User createUser(RegistrationRequest request){
       User user = User.builder()
               .fullName(request.fullName())
               .email(request.email())
               .username(request.username())
               .password(passwordEncoder.encode(request.password()))
               .role(Role.USER)
               .build();
       userRepository.save(user);
       return user;
    }

    public AuthenticationResponse signin(AuthenticationRequest request)
    {
        var user = userRepository.findUserByEmailOrUsername(request.getEmail(), request.getUsername()).orElseThrow(()
                -> new RuntimeException("User Not Found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Incorrect password");

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateTokenRefreshToken(new HashMap<>(), user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken)
                .build();
    }

}
