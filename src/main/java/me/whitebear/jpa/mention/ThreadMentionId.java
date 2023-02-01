package me.whitebear.jpa.mention;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ThreadMentionId implements Serializable {

  @Serial
  private static final long serialVersionUID = 932813899396136126L;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "thread_id")
  private Long threadId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ThreadMentionId mentionId = (ThreadMentionId) o;
    return Objects.equals(getUserId(), mentionId.getUserId()) && Objects.equals(getThreadId(),
        mentionId.getThreadId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUserId(), getThreadId());
  }
}