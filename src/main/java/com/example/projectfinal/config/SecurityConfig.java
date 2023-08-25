package com.example.projectfinal.config;

import com.example.projectfinal.security.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Autowired
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // Câu truy vấn để lấy thông tin người dùng (username, password) từ bảng accounts
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, enable FROM accounts WHERE username = ?");
        // Câu truy vấn để lấy danh sách quyền (role) của người dùng từ bảng roles và accounts_roles
        // Chủ yếu lấy ra username và tên role
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, r.name FROM accounts u" +
                        " INNER JOIN accounts_roles ur ON u.id = ur.account_id" +
                        " INNER JOIN roles r ON ur.role_id = r.id" +
                        " WHERE u.username = ?"
        );
        return jdbcUserDetailsManager;
    }

    // Phân quyền bằng roles
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                // Request nào cũng cần phải đăng nhập

                configurer -> configurer.requestMatchers("/css/**").permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers("/account/change-password/**").hasAnyRole("ADMIN", "TEACHER", "USER")
                        .requestMatchers("/student/home/**").hasAnyRole("USER")
                        .requestMatchers("/teacher/home/**").hasAnyRole("TEACHER")
                        .requestMatchers("/student-information/{idStudent}").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/teacher-information/{idTeacher}").hasAnyRole("TEACHER")
                        .requestMatchers("/student-te/**").hasRole("TEACHER")
                        .requestMatchers("/student/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/parent/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/report-card-detail/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/report-card/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/class/**").hasRole("ADMIN")
                        .requestMatchers("/school/**").hasRole("ADMIN")
                        .requestMatchers("/subject/**").hasRole("ADMIN")
                        .requestMatchers("/teacher/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        ).formLogin(
                // Custom trang login (showLoginPage là 1 endpoint trong controller)
                form -> form.loginPage("/login")
                        // Khi nhấn nút đăng nhập thì sẽ gửi vào (authenticateTheUser mặc định của security)
                        .loginProcessingUrl("/authenticateTheUser").permitAll()
                        .successHandler(authenticationSuccessHandler)
        ).logout(
                // Ai cũng có thể đăng xuất được (logout -> logout.permitAll())
                LogoutConfigurer::permitAll
        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
