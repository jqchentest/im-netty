package com.jqchen.netty.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {
    private Long id;
    private String name;
    private OnlineStatusEnum onlineStatus;

    public static Friend of(User user) {
        Friend friend = new Friend();
        BeanUtils.copyProperties(user, friend);
        friend.setOnlineStatus(OnlineStatusEnum.OFFLINE);
        return friend;
    }
}
