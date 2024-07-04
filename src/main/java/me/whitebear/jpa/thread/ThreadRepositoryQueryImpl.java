package me.whitebear.jpa.thread;

import static me.whitebear.jpa.follow.QFollow.follow;
import static me.whitebear.jpa.thread.QThread.thread;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ThreadRepositoryQueryImpl implements ThreadRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<Thread> findThreadsByFollowingUser(FollowingThreadSearchCond cond,
      Pageable pageable) {
    var query = query(thread, cond)
        .orderBy(createOrderSpecifier(cond.getSortType()))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    var threads = query.fetch();
    long totalSize = query(Wildcard.count, cond).fetch().get(0);

    return PageableExecutionUtils.getPage(threads, pageable, () -> totalSize);
  }

  private <T> JPAQuery<T> query(Expression<T> expr, FollowingThreadSearchCond cond) {
    return jpaQueryFactory.select(expr)
        .from(thread)
        .where(thread.user.id.in(
            jpaQueryFactory
                .select(follow.followedUser.id)
                .from(follow)
                .where(follow.followingUser.id.eq(cond.getFollowingUserId()))
        ));
  }

  @Override
  public Page<Thread> search(ThreadSearchCond cond, Pageable pageable) {
    var query = query(thread, cond)
        .orderBy(createOrderSpecifier(cond.getSortType()))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    var threads = query.fetch();
    long totalSize = countQuery(cond).fetch().get(0);

    threads.stream()
        .map(Thread::getComments)
        .forEach(comments -> comments
            .forEach(comment -> Hibernate.initialize(comment.getEmotions())));

    return PageableExecutionUtils.getPage(threads, pageable, () -> totalSize);
  }

  private <T> JPAQuery<T> query(Expression<T> expr, ThreadSearchCond cond) {
    return jpaQueryFactory.select(expr)
        .from(thread)
        .leftJoin(thread.channel).fetchJoin()
        .leftJoin(thread.emotions).fetchJoin()
        .leftJoin(thread.comments).fetchJoin()
        .leftJoin(thread.mentions).fetchJoin()
        .where(
            channelIdEq(cond.getChannelId()),
            mentionedUserIdEq(cond.getMentionedUserId())
        );
  }


  private JPAQuery<Long> countQuery(ThreadSearchCond cond) {
    return jpaQueryFactory.select(Wildcard.count)
        .from(thread)
        .where(
            channelIdEq(cond.getChannelId()),
            mentionedUserIdEq(cond.getMentionedUserId())
        );
  }

  private BooleanExpression channelIdEq(Long channelId) {
    return Objects.nonNull(channelId) ? thread.channel.id.eq(channelId) : null;
  }

  private BooleanExpression mentionedUserIdEq(Long mentionedUserId) {
    return Objects.nonNull(mentionedUserId) ? thread.mentions.any().user.id.eq(mentionedUserId)
        : null;
  }

  private OrderSpecifier createOrderSpecifier(FollowingThreadSearchCond.SortType sortType) {
    return switch (sortType) {
      case CREATE_AT_DESC -> thread.createdAt.desc();
      case USER_NAME_ASC -> thread.user.username.asc();
    };
  }

  private OrderSpecifier createOrderSpecifier(ThreadSearchCond.SortType sortType) {
    return switch (sortType) {
      case CREATE_AT_DESC -> thread.createdAt.desc();
      case USER_NAME_ASC -> thread.user.username.asc();
      case MENTIONED_CREATE_AT_DESC -> thread.mentions.any().createdAt.desc();
    };
  }
}
