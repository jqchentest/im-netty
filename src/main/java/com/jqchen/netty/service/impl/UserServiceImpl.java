package com.jqchen.netty.service.impl;

import com.jqchen.netty.bean.User;
import com.jqchen.netty.service.UserService;
import com.jqchen.netty.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User get(Long id) {
        return UserUtils.users.get(id);
    }

    @Override
    public User login(String userName, String password) {
        return UserUtils.users.entrySet().stream()
                .filter(entry -> userName.equals(entry.getValue().getName()) && password.equals(entry.getValue().getPassword()))
                .findFirst()
                .map(Map.Entry::getValue).orElse(null);
    }

    @Override
    public List<String> getLeaveMessages(Long userId) {
        if (UserUtils.offlineMessage.containsKey(userId)) {
            return UserUtils.offlineMessage.get(userId);
        }
        return null;
    }


    @Override
    public void sendLeaveMessage(Long userId, String message) {
        if (UserUtils.offlineMessage.containsKey(userId)) {
            UserUtils.offlineMessage.get(userId).add(message);
        } else {
            List<String> list = new ArrayList<>();
            list.add(message);
            UserUtils.offlineMessage.put(userId, list);
        }
    }
}
