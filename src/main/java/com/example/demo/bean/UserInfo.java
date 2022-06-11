package com.example.demo.bean;

import com.example.demo.entity.Player;
import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    int userId;
    String account;
    String token;
    List<Player> data;
}
