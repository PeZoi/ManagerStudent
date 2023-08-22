package com.example.projectfinal.service.implement;

import com.example.projectfinal.entity.Account;
import com.example.projectfinal.repository.AccountRepository;
import com.example.projectfinal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountImple implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void changePassword(long id, String newPassword) {
        Optional<Account> account = accountRepository.findById(id);
        account.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
        accountRepository.saveAndFlush(account.get());
    }
}
