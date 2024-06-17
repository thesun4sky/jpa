package me.whitebear.jpa.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long>,
        QuerydslPredicateExecutor<Channel>, ChannelRepositoryQuery {

    @Query("select c from Channel c join fetch c.threads")
    List<Channel> findAllByQueryAnnotation();
}
