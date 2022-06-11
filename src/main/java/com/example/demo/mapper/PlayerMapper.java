package com.example.demo.mapper;


import com.example.demo.entity.Player;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayerMapper {

    @Select("select * from player where id = #{userid}")
    Player findPlayerByUserId(int userid);

    @Select("select * from player where account = #{account}")
    Player findPlayerByUsername(String account);

    @Select("select * from player where account = #{account} and pwd = #{pwd}")
    Player loginByJyAccount(String account, String pwd);

    @Update("INSERT INTO `player` (`account`, `pwd`, `phone`) VALUES (#{account}, #{pwd}, #{phone});")
    @Transactional
    void registerAccount(Player player);

    @Update("INSERT INTO `player` (`pid`, `nickname`) VALUES (#{pid}, #{nickname});")
    @Transactional
    void insertChild(Player player);

    @Select("select * from player where pid = #{pid}")
    List<Player> findChildPlayerByPid(int pid);
}
