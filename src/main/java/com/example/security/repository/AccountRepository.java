package com.example.security.repository;

import com.example.security.login.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountDTO, Long> {
    AccountDTO findByUsername(String username); // Username으로 계정을 찾는 메소드
}
