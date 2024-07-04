package me.whitebear.jpa.emotion;

import static me.whitebear.jpa.emotion.QCommentEmotion.commentEmotion;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import me.whitebear.jpa.comment.Comment;
import me.whitebear.jpa.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CommentEmotionRepositoryQueryImpl implements CommentEmotionRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<Comment> findLikedComments(User user, Pageable pageable) {
    // 사용자가 작성한 댓글 감정을 조회합니다.
    var query = query(commentEmotion.comment, user)
        .orderBy(commentEmotion.createdAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    // 총 갯수를 조회합니다.
    var threads = query.fetch();
    long totalSize = query(Wildcard.count, user).fetch().get(0);

    // 페이지를 생성합니다.
    return PageableExecutionUtils.getPage(threads, pageable, () -> totalSize);
  }

  @Override
  public Long getCountLikedComments(User user) {
    return query(Wildcard.count, user).fetchOne();
  }

  private <T> JPAQuery<T> query(Expression<T> expr, User user) {
    return jpaQueryFactory.select(expr)
        .from(commentEmotion)
        .join(commentEmotion.comment)
        .where(
            userEq(user)
        );
  }

  private BooleanExpression userEq(User user) {
    return Objects.nonNull(user) ? commentEmotion.user.eq(user) : null;
  }
}
