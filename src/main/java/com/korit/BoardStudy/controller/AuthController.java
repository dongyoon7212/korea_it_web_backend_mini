package com.korit.BoardStudy.controller;

import com.korit.BoardStudy.dto.ApiRespDto;
import com.korit.BoardStudy.dto.auth.SigninReqDto;
import com.korit.BoardStudy.dto.auth.SignupReqDto;
import com.korit.BoardStudy.security.model.PrincipalUser;
import com.korit.BoardStudy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/principal")
    public ResponseEntity<?> getPricipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        ApiRespDto<?> apiRespDto = new ApiRespDto<>("success", "", principalUser);
        return ResponseEntity.ok(apiRespDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        return ResponseEntity.ok(authService.signup(signupReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(authService.signin(signinReqDto));
    }
}
