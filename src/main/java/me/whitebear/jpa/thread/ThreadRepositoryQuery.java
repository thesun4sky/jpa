package me.whitebear.jpa.thread;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThreadRepositoryQuery {

  Page<Thread> findThreadsByFollowingUser(FollowingThreadSearchCond cond,
      Pageable pageable);

  Page<Thread> search(ThreadSearchCond cond, Pageable pageable);

}
