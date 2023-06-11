package com.code.AssJava5.service.Impl;

import com.code.AssJava5.entity.Account;
import com.code.AssJava5.repositories.AccountRepo;
import com.code.AssJava5.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepo accountRepo;

    @Override
    public Account findByUser(String user) {
        return accountRepo.findByUser(user);
    }

    @Override
    public String setHashMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    @Override
    public String getHashMD5(String password) throws NoSuchAlgorithmException {
        byte[] bytes = Base64.getDecoder().decode(setHashMD5(password));
        return new String(bytes);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepo.findByEmail(email);
    }

    @Override
    public Account save(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Account findByPass(String pass) {
        return accountRepo.findByPass(pass);
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepo.findAll(pageable);
    }

    public Optional<Account> findById(String username){
        return accountRepo.findById(username);
    }

    @Override
    public void delete(String username) {
        accountRepo.deleteById(username);
    }
}
