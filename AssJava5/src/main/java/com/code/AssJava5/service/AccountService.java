package com.code.AssJava5.service;

import com.code.AssJava5.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface AccountService {
    public Account findByUser(String user);

    String setHashMD5(String password) throws NoSuchAlgorithmException;

    String getHashMD5(String password) throws NoSuchAlgorithmException;

    Account findByEmail(String email);

    Account save(Account account);

    public Account findByPass(String pass);

     Page<Account> findAll(Pageable pageable);

    Optional<Account> findById(String username);

    void delete(String username);
}
