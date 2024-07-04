package me.whitebear.jpa.thread;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.common.PageDTO;
import me.whitebear.jpa.thread.ThreadSearchCond.SortType;
import me.whitebear.jpa.user.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ThreadServiceImpl implements ThreadService {

  private final ThreadRepository threadRepository;

  @Override
  public Thread insert(Thread thread) {
    return threadRepository.save(thread);
  }

  @Override
  public ThreadDTO selectThread(Long threadId) {
    return threadRepository.findById(threadId)
        .map(ThreadDTO::new)
        .orElseThrow(() -> new IllegalArgumentException("쓰레드를 찾을 수 없습니다."));
  }

  @Override
  public List<ThreadDTO> selectNotEmptyThreadList(Channel channel) {
    var thread = QThread.thread;

    // 메세지가 비어있지 않은 해당 채널의 쓰레드 목록
    var predicate = thread.channel
        .eq(channel)
        .and(thread.message.isNotEmpty());
    // var threads = threadRepository.findAll(predicate);

    return List.of();// IteratorAdapter.asList(threads.iterator());
  }

  @Transactional(readOnly = true)
  @Override
  public Page<ThreadDTO> selectMentionedThreadList(Long userId, PageDTO pageDTO) {
    var cond = ThreadSearchCond.builder().mentionedUserId(userId)
        .sortType(SortType.MENTIONED_CREATE_AT_DESC).build();
    return threadRepository.search(cond, pageDTO.toPageable("metions")).map(ThreadDTO::new);
  }

  @Override
  public Page<ThreadDTO> selectFollowedUserThreads(User user, PageDTO pageDTO,
      FollowingThreadSearchCond.SortType sortType) {
    return threadRepository.findThreadsByFollowingUser(FollowingThreadSearchCond.builder()
        .followingUserId(user.getId())
        .sortType(sortType)
        .build(), pageDTO.toPageable()).map(ThreadDTO::new);
  }
}
