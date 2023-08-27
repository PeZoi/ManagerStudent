package com.example.projectfinal.security;

import com.example.projectfinal.entity.Account;
import com.example.projectfinal.repository.AccountRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Lấy thông tin người dùng từ đối tượng Authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Lấy thông tin của user đó ra
        Optional<Account> accountOptional = accountRepository.findByUsername(userDetails.getUsername());
        // Lưu vào session
        HttpSession session = request.getSession();
        session.setAttribute("account_session", accountOptional.get());

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect("/student/home");
                return;
            } else if (authority.getAuthority().equals("ROLE_TEACHER")) {
                response.sendRedirect("/teacher/home");
                return;
            } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/school");
                return;
            }
        }

        // Default redirect if no specific role match found
        response.sendRedirect("/defaultUrl");
    }
}

