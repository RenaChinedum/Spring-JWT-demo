package com.rena.springsecuritydemo.dto;

public record RegistrationRequest(String fullName, String email, String password, String username) {
}
