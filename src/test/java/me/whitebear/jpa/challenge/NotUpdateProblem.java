package me.whitebear.jpa.challenge;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.channel.ChannelRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // 어플리케이션 의존성을 모두 사용할 수 있도록
@Rollback(value = false) // 1번 테스트 데이터를 2번, 3번에서 사용할 수 있도록
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 테스트가 순서를 보장하도록
public class NotUpdateProblem {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ChannelRepository channelRepository;

    static Long channelId = 1L;

    @Test
    @Order(1)
    @Transactional
    @DisplayName("init")
    void init() {
        // given
        var channel = new Channel("name", Channel.Type.PUBLIC);
        channel.updateId(channelId);
        channelRepository.save(channel);

        // 영속성 컨텍스트 반영 및 초기화
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @Order(2)
    @DisplayName("save() 메서드로 수정(merge)시 반환 인스턴스와 조회 인스턴스가 다른 이슈")
    void test_notUpdate_issue() {
        // when
        var updateChannel = channelRepository.findById(channelId);
        updateChannel.get().updateName("oldName");
        var savedChannel = channelRepository.save(updateChannel.get());
        updateChannel.get().updateName("newName"); // 조회 인스턴스를 수정

        // then
        assert savedChannel.getName() == "oldName"; // 아직 이전값으로 저장되어있음
    }


    @Test
    @Order(3)
    @DisplayName("save() 메서드로 수정(merge)시 반환 인스턴스와 조회 인스턴스가 다른 이슈 해결")
    void test_notUpdate_issue_solve() {
        // when
        var updateChannel = channelRepository.findById(channelId);
        updateChannel.get().updateName("oldName");
        var savedChannel = channelRepository.save(updateChannel.get());
        savedChannel.updateName("newName"); // 반환 인스턴스값을 수정

        // then
        assert savedChannel.getName() == "newName"; // 최신값으로 저장되어있음
    }
}
