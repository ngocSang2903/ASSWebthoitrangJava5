package com.code.AssJava5.repositories;

import com.code.AssJava5.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepo extends JpaRepository<Account,String> {

    @Query("SELECT a FROM Account a WHERE a.username=:user")
    public Account findByUser(@Param("user") String user);

    @Query("SELECT a FROM Account a WHERE a.email=?1")
    public Account findByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.password=?1")
    public Account findByPass(String pass);
}
