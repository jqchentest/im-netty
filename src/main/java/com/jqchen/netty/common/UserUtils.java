package com.jqchen.netty.common;

import com.jqchen.netty.bean.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户工具
 */
public class UserUtils {
    public static Map<Long, User> users = new HashMap<>();
    /**
     * 用户离线消息map
     */
    public static Map<Long, List<String>> offlineMessage = new HashMap<>();

    static {
        users.put(1L, User.builder().id(1L).name("毛毛").build());
        users.put(2L, User.builder().id(2L).name("哈哈").build());
        users.put(3L, User.builder().id(3L).name("啧啧啧").build());
    }
}
