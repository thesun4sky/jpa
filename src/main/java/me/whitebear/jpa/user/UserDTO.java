package me.whitebear.jpa.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {

  private Long id;
  private String username;
  private String password;
  private String profileImageUrl;

  private Long threadEmotionCount;
  private Long commentEmotionCount;

  @Builder
  public UserDTO(User user, Long threadEmotionCount, Long commentEmotionCount) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.profileImageUrl = user.getProfileImageUrl();
    this.threadEmotionCount = threadEmotionCount;
    this.commentEmotionCount = commentEmotionCount;
  }
}
