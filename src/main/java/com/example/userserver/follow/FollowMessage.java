package com.example.userserver.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowMessage {
    private int userId;
    private int followerId;
    private boolean follow;

}
