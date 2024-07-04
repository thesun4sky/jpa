package me.whitebear.jpa.thread;

import java.util.List;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.common.PageDTO;
import me.whitebear.jpa.user.User;
import org.springframework.data.domain.Page;

public interface ThreadService {

  /**
   * 쓰레드 조회
   *
   * @param threadId 쓰레드 ID
   * @return 쓰레드 응답 정보
   */
  ThreadDTO selectThread(Long threadId);

  List<ThreadDTO> selectNotEmptyThreadList(Channel channel);

  Page<ThreadDTO> selectMentionedThreadList(Long userId, PageDTO pageDTO);

  Page<ThreadDTO> selectFollowedUserThreads(User user, PageDTO pageDTO);

  Thread insert(Thread thread);
}
