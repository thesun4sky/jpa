package me.whitebear.jpa.userChannel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserChannelId implements Serializable {

  @Serial
  private static final long serialVersionUID = 932813899396663626L;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "channel_id")
  private Long channelId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserChannelId userChannelId = (UserChannelId) o;
    return Objects.equals(getUserId(), userChannelId.getUserId()) && Objects.equals(getChannelId(),
        userChannelId.getChannelId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUserId(), getChannelId());
  }
}