package com.example.projectfinal.rest;

import com.example.projectfinal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountRest {
    @Autowired
    AccountService accountService;
    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable(name = "id") long id, @RequestParam(name = "newPassword") String newPassword){
        try{
            accountService.changePassword(id, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
