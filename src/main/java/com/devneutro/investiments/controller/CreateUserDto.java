package com.devneutro.investiments.controller;

public record CreateUserDto(
        String username, String email, String password
) {
}
