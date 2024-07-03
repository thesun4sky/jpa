package me.whitebear.jpa.emotion;

import lombok.RequiredArgsConstructor;
import me.whitebear.jpa.comment.Comment;
import me.whitebear.jpa.thread.Thread;
import me.whitebear.jpa.user.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmotionService {

  private final ThreadEmotionRepository threadEmotionRepository;

  private final CommentEmotionRepository commentEmotionRepository;

  /**
   * 쓰레드에 감정 추가
   *
   * @param user   사용자
   * @param thread 스레드
   * @param body   내용
   * @return 추가된 감정
   */
  public Emotion add(User user, Thread thread, String body) {
    if (threadEmotionRepository.existsByUserAndThread(user, thread)) {
      throw new IllegalArgumentException("이미 쓰레드에 감정을 추가했습니다.");
    }
    return threadEmotionRepository.save(ThreadEmotion.builder()
        .user(user)
        .thread(thread)
        .body(body)
        .build());
  }

  /**
   * 댓글에 감정 추가
   *
   * @param user    사용자
   * @param comment 스레드
   * @param body    내용
   * @return 추가된 감정
   */
  public Emotion add(User user, Comment comment, String body) {
    if (commentEmotionRepository.existsByUserAndComment(user, comment)) {
      throw new IllegalArgumentException("이미 댓글에 감정을 추가했습니다.");
    }
    return commentEmotionRepository.save(CommentEmotion.builder()
        .user(user)
        .comment(comment)
        .body(body)
        .build());
  }

  /**
   * 감정 삭제
   *
   * @param emotionId 감정 ID
   */
  public void delete(Long emotionId) {
    threadEmotionRepository.deleteById(emotionId);
  }

}
