package me.whitebear.jpa.emotion;

import lombok.RequiredArgsConstructor;
import me.whitebear.jpa.comment.Comment;
import me.whitebear.jpa.comment.CommentDTO;
import me.whitebear.jpa.thread.Thread;
import me.whitebear.jpa.thread.ThreadDTO;
import me.whitebear.jpa.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmotionServiceImpl implements EmotionService {

  private final ThreadEmotionRepository threadEmotionRepository;

  private final CommentEmotionRepository commentEmotionRepository;

  @Override
  public Long getCountLikedThreads(User user) {
    return threadEmotionRepository.getCountLikedThreads(user);
  }

  @Override
  public Long getCountLikedComments(User user) {
    return commentEmotionRepository.getCountLikedComments(user);
  }

  @Override
  public Page<ThreadDTO> getLikedThreads(User user, int page, int size) {
    return threadEmotionRepository.findLikedThreads(user, PageRequest.of(page, size))
        .map(ThreadDTO::new);
  }

  @Override
  public Page<CommentDTO> getLikedComments(User user, int page, int size) {
    return commentEmotionRepository.findLikedComments(user, PageRequest.of(page, size))
        .map(CommentDTO::new);
  }

  @Override
  public ThreadEmotion add(User user, Thread thread, String body) {
    if (threadEmotionRepository.existsByUserAndThread(user, thread)) {
      throw new IllegalArgumentException("이미 쓰레드에 감정을 추가했습니다.");
    }
    return threadEmotionRepository.save(ThreadEmotion.builder()
        .user(user)
        .thread(thread)
        .body(body)
        .build());
  }

  @Override
  public CommentEmotion add(User user, Comment comment, String body) {
    if (commentEmotionRepository.existsByUserAndComment(user, comment)) {
      throw new IllegalArgumentException("이미 댓글에 감정을 추가했습니다.");
    }
    return commentEmotionRepository.save(CommentEmotion.builder()
        .user(user)
        .comment(comment)
        .body(body)
        .build());
  }

  @Override
  public void delete(Long emotionId) {
    threadEmotionRepository.deleteById(emotionId);
  }

}
