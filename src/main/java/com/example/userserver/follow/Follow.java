package com.example.userserver.follow;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int followId;
    private int userId;
    private int followerId;
    @CreatedDate
    private ZonedDateTime followDatetime;

    public Follow(int userId, int followerId) {
        this.userId = userId;
        this.followerId = followerId;
    }
}