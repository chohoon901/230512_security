package com.example.security;

import com.example.security.login.AccountDTO;
import com.example.security.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 설정을 등록
@EnableWebSecurity // 웹 보안 설정 변경
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                username -> {
                    AccountDTO accountDTO = accountRepository.findByUsername(username);
                    if(accountDTO != null){
                        return User.withUsername(accountDTO.getUsername())
                                .password(accountDTO.getPassword())
                                .roles(accountDTO.getRole())
                                .build(); // 스프링 security에서 관리하는 유저를 생성(세션)
                    } else {
                        // 스프링 security에서 이미 갖고 있는 에러
                        throw new UsernameNotFoundException("해당 유저가 없습니다.");
                    }
                }).passwordEncoder(passwordEncoder()); // 암호를 복호화(bcrypt)
    }

    @Bean // 스프링에 등록(추후에 재채용 가능성)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 스프링 security의 사전작업

    // configure -> http 요청이 들어왔을 떄의 설정

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll() // 인증없이 lgoin 페이지 사용 가능
                .antMatchers("/register").permitAll() // 인증없이 register 페이지 사용 가능
                .anyRequest().authenticated() // 나머지의 요청은 인증필요
                .and()
                .formLogin() // form을 이용해서 로그인 할 것이다.
                .loginPage("/login") // 로그인 페이지 URL
                .defaultSuccessUrl("/home") // 로그인 성공 후 이용할 URL
                .permitAll();
//                .and()
//                .logout()
//                .and()
//                .csrf().disable();
    }
}
