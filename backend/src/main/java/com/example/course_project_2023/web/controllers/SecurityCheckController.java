package com.example.course_project_2023.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/knowyourteacher-api/v1/security_checks")
public class SecurityCheckController {
    @GetMapping("unauthorized_get")
    public ResponseEntity<String> unauthorizedGet() {
        return new ResponseEntity<>("Success from unauthorized get!", HttpStatus.OK);
    }

    @PostMapping("unauthorized_post")
    public ResponseEntity<String> unauthorizedPost(@RequestBody String someInput) {
        return new ResponseEntity<>("Success from unauthorized post! You posted: " + someInput, HttpStatus.OK);
    }

    @GetMapping("authorized_as_Admin_get")
    public ResponseEntity<String> authorizedAsAdminGet() {
        return new ResponseEntity<>("Success from authorized as Admin get!", HttpStatus.OK);
    }

    @PostMapping("authorized_as_Admin_post")
    public ResponseEntity<String> authorizedAsAdminPost(@RequestBody String someInput) {
        return new ResponseEntity<>("Success from authorized as Admin post! You posted: " + someInput, HttpStatus.OK);
    }

    @GetMapping("authorized_as_User_get")
    public ResponseEntity<String> authorizedAsUserGet() {
        return new ResponseEntity<>("Success from authorized as User get!", HttpStatus.OK);
    }

    @PostMapping("authorized_as_User_post")
    public ResponseEntity<String> authorizedAsUserPost(@RequestBody String someInput) {
        return new ResponseEntity<>("Success from authorized as User post! You posted: " + someInput, HttpStatus.OK);
    }

    @GetMapping("role_check")
    public ResponseEntity<String> checkRoleGet(Principal principal) {
        if (principal != null)
            return new ResponseEntity<>("You are logged in as: " + principal.getName(), HttpStatus.OK);
        else return new ResponseEntity<>("You are logged in as: guest", HttpStatus.OK);

    }
}
