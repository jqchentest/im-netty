package com.jqchen.netty.utils;

import com.jqchen.netty.bean.Friend;
import com.jqchen.netty.bean.User;

import java.util.*;

/**
 * 用户工具
 */
public class UserUtils {
    public static Map<Long, User> users = new HashMap<>();
    public static Map<Long, Set<Friend>> friends = new HashMap<>();

    /**
     * 用户离线消息map
     */
    public static Map<Long, List<String>> offlineMessage = new HashMap<>();

    static {
        users.put(1L, User.builder().id(1L).name("毛毛").password("123").build());
        users.put(2L, User.builder().id(2L).name("哈哈").password("123").build());
        users.put(3L, User.builder().id(3L).name("啧啧啧").password("123").build());
        users.put(4L, User.builder().id(4L).name("法外狂徒").password("123").build());
        users.put(5L, User.builder().id(5L).name("傻子").password("123").build());
        users.put(6L, User.builder().id(6L).name("好嗨哟").password("123").build());
        //随机插入朋友关系
        users.forEach((key, value) -> {
            users.forEach((k, v) -> {
                if (v.equals(value)) {
                    return;
                }
                Random random = new Random();
                if (random.nextBoolean()) {
                    addFriend(key, Friend.of(v));
                    addFriend(v.getId(), Friend.of(value));
                }
            });
        });
    }

    private static void addFriend(Long userId, Friend friend) {
        if (friends.containsKey(userId)) {
            friends.get(userId).add(friend);
        } else {
            Set<Friend> friendList = new HashSet<>();
            friendList.add(friend);
            friends.put(userId, friendList);
        }
    }
}
