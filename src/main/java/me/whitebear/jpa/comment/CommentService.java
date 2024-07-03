package me.whitebear.jpa.comment;

public interface CommentService {

  /**
   * 쓰레드 조회
   *
   * @param threadId 쓰레드 ID
   * @return 쓰레드 응답 정보
   */
  CommentDTO selectComment(Long commentId);
}
