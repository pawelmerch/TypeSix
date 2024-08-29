package io.mipt.typesix.web.controller;

import io.mipt.typesix.businesslogic.dto.UserWithRoleDto;
import io.mipt.typesix.businesslogic.service.core.api.RoleService;
import io.mipt.typesix.web.security.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static io.mipt.typesix.web.EndpointsList.*;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final RoleService roleService;

    @GetMapping(IS_ADMIN_ENDPOINT)
    @PreAuthorize("hasRole('" + Utils.ADMIN_ROLE + "')")
    public ResponseEntity<?> isAdmin(Authentication authentication) {
        return ResponseEntity.ok(Map.of("email", Utils.retrieveEmailFromAuthentication(authentication)));
    }

    @PreAuthorize("hasRole('" + Utils.ADMIN_ROLE + "')")
    @GetMapping(ADMIN_ALL_USERS_LIST_ENDPOINT)
    public ResponseEntity<List<UserWithRoleDto>> userList(@RequestParam(name = "offset") int offset, @RequestParam(name = "count") int count) {
        return ResponseEntity.ok(roleService.getAllUsersWithRoles(offset, count));
    }

    @PreAuthorize("hasRole('" + Utils.ADMIN_ROLE + "')")
    @PostMapping(ADMIN_SET_ROLE_ENDPOINT)
    public ResponseEntity<?> setRole(@PathVariable int id, @RequestParam(name = "role") String role) {
        roleService.setRole(id, role);
        return ResponseEntity.ok().build();
    }
}
