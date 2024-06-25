package me.whitebear.jpa.channel;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static me.whitebear.jpa.channel.QChannel.channel;

@RequiredArgsConstructor
public class ChannelRepositoryQueryImpl implements ChannelRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory; // 뭘 넣어줘야 하는지 몰라서 에러가남

    @Override
    public List<Channel> findFetchAll() {
        return jpaQueryFactory.select(channel)
                .from(channel)
                .leftJoin(channel.threads).fetchJoin()
                .fetch();
    }
}
