package com.jqchen.netty.controller;

import com.jqchen.netty.bean.Friend;
import com.jqchen.netty.bean.User;
import com.jqchen.netty.service.FriendService;
import com.jqchen.netty.service.UserService;
import com.jqchen.netty.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


/**
 * 用户后台接口服务
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final FriendService friendService;

    public UserController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    /**
     * 登录
     */
    @GetMapping("login")
    public ResponseVO<User> login(String userName, String password) {
        User user = userService.login(userName, password);
        return user == null ? new ResponseVO<>("用户名密码错误") : new ResponseVO<>(user);
    }

    /**
     * 好友列表
     */
    @GetMapping("friends")
    public ResponseVO<Set<Friend>> friends(Long userId) {
        return new ResponseVO<>(friendService.getFriends(userId));
    }
}
