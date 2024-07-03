package me.whitebear.jpa.emotion;

import me.whitebear.jpa.comment.Comment;
import me.whitebear.jpa.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentEmotionRepository extends JpaRepository<CommentEmotion, Long>,
    QuerydslPredicateExecutor<CommentEmotion>, CommentEmotionRepositoryQuery {

  boolean existsByUserAndComment(User user, Comment comment);
}
