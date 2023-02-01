package me.whitebear.jpa.mention;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CommentMentionId implements Serializable {

  @Serial
  private static final long serialVersionUID = 932813812396136126L;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "comment_id")
  private Long commentId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentMentionId mentionId = (CommentMentionId) o;
    return Objects.equals(getUserId(), mentionId.getUserId()) && Objects.equals(getCommentId(),
        mentionId.getCommentId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUserId(), getCommentId());
  }
}