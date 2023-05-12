package com.example.security.service;

import com.example.security.login.AccountDTO;
import com.example.security.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired // 생성자 함수 대체
    private AccountRepository accountRepository;

    public AccountDTO register(AccountDTO accountDTO) throws Exception {
        // Id 중복 거르기
        if (accountRepository.findByUsername(accountDTO.getUsername()) != null) {
            throw new Exception("중복 유저");
        }
        return accountRepository.save(accountDTO);
    }
}
