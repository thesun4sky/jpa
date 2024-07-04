package me.whitebear.jpa.emotion;

import me.whitebear.jpa.thread.Thread;
import me.whitebear.jpa.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ThreadEmotionRepository extends JpaRepository<ThreadEmotion, Long>,
    QuerydslPredicateExecutor<ThreadEmotion>, ThreadEmotionRepositoryQuery {

  boolean existsByUserAndThread(User user, Thread thread);
}
