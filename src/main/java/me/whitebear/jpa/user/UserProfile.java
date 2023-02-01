package me.whitebear.jpa.user;

import org.springframework.beans.factory.annotation.Value;

public interface UserProfile {

  String getUsername();

  String getProfileImageUrl();

  @Value("#{target.profileImageUrl != null}")
  boolean hasProfileImage();

  default String getUserInfo() {
    return getUsername() + " " + (hasProfileImage() ? getProfileImageUrl() : "");
  }
}
