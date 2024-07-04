package me.whitebear.jpa.follow;

import lombok.RequiredArgsConstructor;
import me.whitebear.jpa.user.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public void follow(User followingUser, User followedUser) {
        if (followRepository.existsById(new FollowId(followingUser.getId(), followedUser.getId()))) {
            throw new IllegalArgumentException("이미 팔로우 중입니다.");
        } else if (followingUser.equals(followedUser)) {
            throw new IllegalArgumentException("자기 자신은 팔로우 할 수 없습니다.");
        }
        followRepository.save(new Follow(followingUser, followedUser));
    }

    @Override
    public void unfollow(Long followingUserId, Long followedUserId) {
        if (!followRepository.existsById(new FollowId(followingUserId, followedUserId))) {
            throw new IllegalArgumentException("팔로우 중이 아닙니다.");
        }
        followRepository.deleteById(new FollowId(followingUserId, followedUserId));
    }

    @Override
    public boolean isFollowing(Long followingUserId, Long followedUserId) {
        return followRepository.existsById(new FollowId(followingUserId, followedUserId));
    }
}
