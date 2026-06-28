package com.finance_app_backend.handlers;

import com.finance_app_backend.responses.ApiResponseWrapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Configuration
@AllArgsConstructor
public class LogoutHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                @Nullable Authentication authentication) throws IOException, ServletException
    {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("FINANCE-APP-SESSION", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        response.setStatus(200);
        response.setContentType("application/json");

        ApiResponseWrapper<Void> apiResponse = new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "User logged out successfully",
                null
        );

        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
