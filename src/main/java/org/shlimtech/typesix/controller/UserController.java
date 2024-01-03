package org.shlimtech.typesix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/user-info")
    public ResponseEntity<?> userInfo(JwtAuthenticationToken token) {
        // TODO principle contains user id as claim. Load full user info from database here.
        return ResponseEntity.ok(Map.of("email", token.getTokenAttributes().get("email")));
    }

}
