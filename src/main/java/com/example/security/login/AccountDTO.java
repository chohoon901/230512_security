package com.example.security.login;

import lombok.Data;

import javax.persistence.*;

@Entity // jpa
@Data //  getter setter
@Table(name = "accounts") // db이름 정하기(name에 따라 jpa가 생성해줌)
public class AccountDTO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;
    String role;
}
