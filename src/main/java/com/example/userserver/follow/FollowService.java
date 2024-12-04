package com.example.userserver.follow;

import com.example.userserver.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final FollowRepository followRepository;

    public boolean isFollow(int userId, int followerId) {
        Optional<Follow> follow = followRepository.findByUserIdAndFollowerId(userId, followerId);
        return follow.isPresent();
    }

    public Follow followUser(int userId, int followerId) {
        if (isFollow(userId, followerId)) {
            throw new IllegalArgumentException("already following.");
        }

        return followRepository.save(new Follow(userId, followerId));
    }

    @Transactional
    public boolean unfollowUser(int userId, int followerId) {
        Optional<Follow> followOpt = followRepository.findByUserIdAndFollowerId(userId, followerId);
        if (followOpt.isEmpty()) {
            return false;
        }

        followRepository.delete(followOpt.get());
        return true;
    }

    public List<UserInfo> listFollower(int userId) {
        return followRepository.findFollowingByUserId(userId);
    }

    public List<UserInfo> listFollowing(int userId) {
        return followRepository.findFollowingByUserId(userId);
    }
}