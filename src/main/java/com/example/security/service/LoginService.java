package com.example.security.service;

import com.example.security.login.AccountDTO;
import com.example.security.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired // 생성자 함수 대체
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AccountDTO register(AccountDTO accountDTO) throws Exception {
        // Id 중복 거르기
        if (accountRepository.findByUsername(accountDTO.getUsername()) != null) {
            throw new Exception("중복 유저");
        }
        // pw bycrypt 암호화
        // 로그인 할때는 bcrypt 적용이 됐는데, 가입할때는 안됨
        String newPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(newPassword);

        return accountRepository.save(accountDTO);
    }
}
