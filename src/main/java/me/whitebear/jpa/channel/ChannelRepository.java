package me.whitebear.jpa.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChannelRepository extends JpaRepository<Channel, Long>,
    QuerydslPredicateExecutor<Channel> {

}
