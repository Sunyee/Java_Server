package com.example.demo.controller;

import com.example.demo.bean.UserInfo;
import com.example.demo.entity.Player;
import com.example.demo.entity.Result;
import com.example.demo.mapper.PlayerMapper;
import com.example.demo.utils.ResultDateUtil;
import com.example.demo.utils.StringUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/player")
public class LoginController {

    @Resource
    PlayerMapper playerMapper;

    @PostMapping("/loginByJY")
    public Result<UserInfo> loginByAccount(@RequestParam String account, @RequestParam String pwd) {
        Player player = playerMapper.findPlayerByUsername(account);
        if (player != null) {
            if (player.getPwd().equals(pwd)) {
                //登录成功，查询绑定的小号
                List<Player> childList = playerMapper.findChildPlayerByPid(player.getId());
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(player.getId());
                userInfo.setAccount(player.getAccount());
                userInfo.setToken(player.getAccount() + "_" + System.currentTimeMillis());
                userInfo.setData(childList);
                return ResultDateUtil.getResultSuc(userInfo, "登录成功");
            } else {
                return ResultDateUtil.getResultFail(new UserInfo(), "密码错误");
            }
        } else {
            return ResultDateUtil.getResultFail(new UserInfo(), "未找到账号");
        }
    }

    @PostMapping("/loginByPh")
    public Player loginByPhone(@RequestParam String ph, @RequestParam String verify) {
        Player player = playerMapper.findPlayerByUsername(ph);
        return player;
    }

    @PostMapping("/find")
    public Player findPlayer(@RequestParam String account) {
        Player player = playerMapper.findPlayerByUsername(account);
        return player;
    }

    @PostMapping("/register")
    public Result<Player> registerAccount(@RequestParam String account, @RequestParam String pwd, @RequestParam(required = false) String phone) {
        System.out.println("player:" + account);
        Player hasPlayer = findPlayer(account);
        if (hasPlayer == null) {
            Player player = new Player();
            player.setAccount(account);
            player.setPwd(pwd);
            if (!StringUtil.isEmpty(phone)) player.setPhone(phone);
            playerMapper.registerAccount(player);
            //注册完成后，添加一个默认小号
            Player playerHost = findPlayer(player.getAccount());
            addChildAccout(playerHost.getId(), "小号1");
            return ResultDateUtil.getResultSuc(player, "注册成功");
        }
        return ResultDateUtil.getResultFail(new Player(), "注册失败，存在用户");
    }

    @PostMapping("/addChild")
    public Result<String> addChildAccout(@RequestParam int id, @RequestParam String nickname) {
        Player player = playerMapper.findPlayerByUserId(id);
        if (player != null) {
            Player childBind = new Player();
            childBind.setPid(player.getId());
            childBind.setNickname(nickname);
            playerMapper.insertChild(childBind);
            return ResultDateUtil.getResultSuc("小号添加成功", "成功");
        }
        return ResultDateUtil.getResultFail("[]", "未找到账号");
    }

}
