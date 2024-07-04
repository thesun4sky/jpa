package me.whitebear.jpa.thread;

import java.util.List;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.common.PageDTO;
import me.whitebear.jpa.thread.FollowingThreadSearchCond.SortType;
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

  /**
   * 팔로우한 사용자의 쓰레드 목록 조회
   *
   * @param user     사용자
   * @param pageDTO  페이지 정보
   * @param sortType 정렬 타입
   * @return 쓰레드 페이지
   */
  Page<ThreadDTO> selectFollowedUserThreads(User user, PageDTO pageDTO, SortType sortType);

  /**
   * 팔로우한 사용자의 쓰레드 목록 조회 (기본 정렬: 생성일자 내림차순)
   *
   * @param user    사용자
   * @param pageDTO 페이지 정보
   * @return 쓰레드 페이지
   */
  default Page<ThreadDTO> selectFollowedUserThreads(User user, PageDTO pageDTO) {
    return selectFollowedUserThreads(user, pageDTO, SortType.CREATE_AT_DESC);
  }

  Thread insert(Thread thread);
}
