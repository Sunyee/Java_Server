package com.example.demo.entity;

import lombok.Data;

@Data
public class Player {
    private Integer id;
    private String account;
    private String pwd;
    private String phone;
    private int pid;
    private String nickname;
}
