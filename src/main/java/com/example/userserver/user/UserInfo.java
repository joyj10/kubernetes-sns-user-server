package com.example.userserver.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int userId;
    private String username;
    private String email;

    public UserInfo(User user) {
        this(user.getUserId(), user.getUsername(), user.getEmail());
    }
}