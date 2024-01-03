package org.shlimtech.typesix.controller;

import lombok.RequiredArgsConstructor;
import org.shlimtech.typesix.dto.UserDTO;
import org.shlimtech.typesix.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user-info")
    public ResponseEntity<UserDTO> userInfo(JwtAuthenticationToken token) {
        UserDTO userDTO = userService.loadUser(Math.toIntExact((Long) token.getTokenAttributes().get("id")));
        return ResponseEntity.ok(userDTO);
    }

}
