package com.example.userserver.follow;

import com.example.userserver.user.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final FollowRepository followRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean isFollow(int userId, int followerId) {
        Optional<Follow> follow = followRepository.findByUserIdAndFollowerId(userId, followerId);
        return follow.isPresent();
    }

    public Follow followUser(int userId, int followerId) {
        if (isFollow(userId, followerId)) {
            throw new IllegalArgumentException("already following.");
        }

        sendFollowerMessage(userId, followerId, true);

        return followRepository.save(new Follow(userId, followerId));
    }

    @Transactional
    public boolean unfollowUser(int userId, int followerId) {
        Optional<Follow> followOpt = followRepository.findByUserIdAndFollowerId(userId, followerId);
        if (followOpt.isEmpty()) {
            return false;
        }

        sendFollowerMessage(userId, followerId, false);
        followRepository.delete(followOpt.get());
        return true;
    }

    private void sendFollowerMessage(int userId, int followerId, boolean isFollow) {
        FollowMessage message = new FollowMessage(userId, followerId, isFollow);
        try {
            kafkaTemplate.send("user.follower", objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserInfo> listFollower(int userId) {
        return followRepository.findFollowingByUserId(userId);
    }

    public List<UserInfo> listFollowing(int userId) {
        return followRepository.findFollowingByUserId(userId);
    }
}