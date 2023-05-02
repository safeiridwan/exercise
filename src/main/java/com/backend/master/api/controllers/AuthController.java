package com.backend.master.api.controllers;

import com.backend.master.domain.dto.request.AuthRequest;
import com.backend.master.domain.dto.response.ResponseAPI;
import com.backend.master.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseAPI signUp(@RequestBody AuthRequest req) {
        return authService.signUp(req);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseAPI> signIn(@RequestBody AuthRequest req) {
        return authService.signIn(req);
    }

}
