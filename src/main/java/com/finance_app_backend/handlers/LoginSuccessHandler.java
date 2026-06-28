package com.finance_app_backend.handlers;

import com.finance_app_backend.lib.Mapper;
import com.finance_app_backend.responses.ApiResponseWrapper;
import com.finance_app_backend.user.User;
import com.finance_app_backend.user.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Configuration
@AllArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final Mapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
        User user = (User) authentication.getPrincipal();

        response.setStatus(200);
        response.setContentType("application/json");

        ApiResponseWrapper<UserDTO> apiResponse = new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "User logged in successfully",
                mapper.userToUserDTO(user)
        );

        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
