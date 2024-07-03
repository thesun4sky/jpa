package me.whitebear.jpa.user;

public interface UserService {

  /**
   * 사용자 프로필 조회
   *
   * @param userId 사용자 ID
   * @return 사용자 프로필
   */
  UserDTO getUserProfile(Long userId);
}
