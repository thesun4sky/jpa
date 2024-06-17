package me.whitebear.jpa.challenge;


import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.channel.ChannelRepository;
import me.whitebear.jpa.configuration.JPAConfiguration;
import me.whitebear.jpa.thread.Thread;
import me.whitebear.jpa.thread.ThreadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfiguration.class)
public class NPlusProblem {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    ChannelRepository channelRepository;

    @BeforeEach
    void setup() {
        var sut = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();

        for (int i = 0; i < 10; i++) {
            var channel = sut.giveMeOne(Channel.class);
            var threads = sut.giveMe(Thread.class, 5);
            threadRepository.saveAll(threads);
            threads.forEach(channel::addThread);

            channelRepository.save(channel);
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("N+1 이슈")
    @Transactional
    void teat_nplush_problem_issue() {
        var allChannel = channelRepository.findAll();
        for (Channel channel : allChannel) {
            System.out.println(channel.getThreads());
        }
    }


    @Test
    @DisplayName("N+1 해결 by query annotation")
    @Transactional
    void teat_nplush_problem_solve_1() {
        var allChannel = channelRepository.findAllByQueryAnnotation();
        for (Channel channel : allChannel) {
            System.out.println(channel.getThreads());
        }
    }


    @Test
    @DisplayName("N+1 해결 by Query DSL")
    @Transactional
    void teat_nplush_problem_solve_2() {
        var allChannel = channelRepository.findFetchAll(); // fetch 모드로 조회
        for (Channel channel : allChannel) {
            System.out.println(channel.getThreads());
        }
    }
}
