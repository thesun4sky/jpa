package me.whitebear.jpa.emotion;

import me.whitebear.jpa.comment.Comment;
import me.whitebear.jpa.comment.CommentDTO;
import me.whitebear.jpa.thread.Thread;
import me.whitebear.jpa.thread.ThreadDTO;
import me.whitebear.jpa.user.User;
import org.springframework.data.domain.Page;

public interface EmotionService {

  Long getCountLikedThreads(User user);

  Long getCountLikedComments(User user);

  Page<ThreadDTO> getLikedThreads(User user, int page, int size);

  Page<CommentDTO> getLikedComments(User user, int page, int size);

  /**
   * 쓰레드에 감정 추가
   *
   * @param user   사용자
   * @param thread 스레드
   * @param body   내용
   * @return 추가된 감정
   */
  ThreadEmotion add(User user, Thread thread, String body);

  /**
   * 댓글에 감정 추가
   *
   * @param user    사용자
   * @param comment 스레드
   * @param body    내용
   * @return 추가된 감정
   */
  CommentEmotion add(User user, Comment comment, String body);


  /**
   * 감정 삭제
   *
   * @param emotionId 감정 ID
   */
  void delete(Long emotionId);
}
