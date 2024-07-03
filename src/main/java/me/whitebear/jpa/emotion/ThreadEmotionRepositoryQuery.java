package me.whitebear.jpa.emotion;

import me.whitebear.jpa.thread.Thread;
import me.whitebear.jpa.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThreadEmotionRepositoryQuery {

  /**
   * 사용자가 작성한 쓰레드 감정을 조회합니다.
   *
   * @param user 사용자
   * @return 쓰레드 감정 목록
   */
  Page<Thread> findLikedThreads(User user, Pageable pageable);

  /**
   * 사용자가 작성한 쓰레드 수를 조회합니다.
   *
   * @param user 사용자
   * @return 쓰레드 수
   */
  Long getCountLikedThreads(User user);
}
