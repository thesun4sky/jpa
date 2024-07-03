package me.whitebear.jpa.emotion;

import me.whitebear.jpa.comment.Comment;
import me.whitebear.jpa.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentEmotionRepositoryQuery {

  /**
   * 사용자가 좋아요한 댓글 목록을 조회합니다.
   *
   * @param user 사용자
   * @return 댓글 목록
   */
  Page<Comment> findLikedComments(User user, Pageable pageable);

  /**
   * 사용자가 좋아요한 댓글 수를 조회합니다.
   *
   * @param user 사용자
   * @return 댓글 수
   */
  Long getCountLikedComments(User user);
}
