package me.whitebear.jpa.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class FollowId implements Serializable {

  @Serial
  private static final long serialVersionUID = 442813812396136126L;

  @Column(name = "following_id")
  private Long followingUserId;

  @Column(name = "followed_id")
  private Long followedUserId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FollowId followId = (FollowId) o;
    return Objects.equals(getFollowingUserId(), followId.getFollowingUserId()) && Objects.equals(
        getFollowedUserId(),
        followId.getFollowedUserId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFollowingUserId(), getFollowedUserId());
  }
}