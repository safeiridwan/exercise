package com.backend.master.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.master.domain.dto.response.ResponseAPI;

@RestController
@RequestMapping("/api/auth/testing")
public class SecureTestingController {

    @GetMapping
    public ResponseAPI reachSecureEndpoint() {
        ResponseAPI response;
        try {
            response = new ResponseAPI(true, "OKE!", null, null);
        }catch (Exception ex){
            response = new ResponseAPI(false, HttpStatus.UNAUTHORIZED.toString(),
                    "Username or password invalid", null);
        }
        return response;
    }
}
