package me.whitebear.jpa.follow;

import me.whitebear.jpa.user.User;

public interface FollowService {

  /**
   * 팔로우
   *
   * @param followingUser 팔로우 하는 사용자
   * @param followedUser  팔로우 당하는 사용자
   */
  void follow(User followingUser, User followedUser);

  /**
   * 언팔로우
   *
   * @param followingUserId 팔로우 하는 사용자 id
   * @param followedUserId  팔로우 당하는 사용자 id
   */
  void unfollow(Long followingUserId, Long followedUserId);

  /**
   * 팔로우 여부
   *
   * @param followingUserId 팔로우 하는 사용자 id
   * @param followedUserId  팔로우 당하는 사용자 id
   * @return 팔로우 여부
   */
  boolean isFollowing(Long followingUserId, Long followedUserId);

}
