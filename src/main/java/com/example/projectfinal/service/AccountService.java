package com.example.projectfinal.service;

import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    public void changePassword(long id, String newPassword);
}
