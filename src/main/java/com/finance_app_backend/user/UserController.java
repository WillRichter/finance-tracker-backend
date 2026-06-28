package com.finance_app_backend.user;

import com.finance_app_backend.requests.CreateUserRequest;
import com.finance_app_backend.responses.ApiResponseWrapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<UserDTO>> createUser(@RequestBody @Valid CreateUserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<ApiResponseWrapper<UserDTO>> getCurrentUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getCurrentUser(user), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseWrapper<UserDTO>> deleteUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.deleteUser(user), HttpStatus.OK);
    }
}