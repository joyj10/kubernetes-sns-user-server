package com.example.userserver.follow;

import com.example.userserver.user.UserInfo;
import com.example.userserver.user.UserRequest;
import com.example.userserver.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowService followService;

    @GetMapping("/followers/{userId}")
    public List<UserInfo> listFollowers(@PathVariable("userId") int userId) {
        return followService.listFollower(userId);
    }

    @GetMapping("/followings/{userId}")
    public List<UserInfo> listFollowings(@PathVariable("userId") int userId) {
        return followService.listFollowing(userId);
    }

    @GetMapping("/follow/{userId}/{followerId}")
    public boolean isFollow(@PathVariable("userId") int userId, @PathVariable("followerId") int followerId) {
        return followService.isFollow(userId, followerId);
    }

    @PostMapping("/follow")
    public Follow followUser(@RequestBody FollowRequest followRequest) {
        return followService.followUser(followRequest.getUserId(), followRequest.getFollowerId());
    }

    @PostMapping("/unfollow")
    public Boolean unfollowUser(@RequestBody FollowRequest unfollowRequest) {
        return followService.unfollowUser(unfollowRequest.getUserId(), unfollowRequest.getFollowerId());
    }
}
