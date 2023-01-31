package me.whitebear.jpa.channel;

import com.querydsl.core.types.Predicate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ChannelRepositoryTest {

  @Autowired
  private ChannelRepository channelRepository;

  @Test
  void insertSelectChannelTest() {
    // given
    var newChannel = Channel.builder().name("new-channel").build();

    // when
    var savedChannel = channelRepository.save(newChannel);

    // then
    var foundChannel = channelRepository.findById(savedChannel.getId());
    assert foundChannel.get().getId().equals(savedChannel.getId());
  }

  @Test
  void queryDslTest() {
    // given
    var newChannel = Channel.builder().name("teasun").build();
    channelRepository.save(newChannel);

    Predicate predicate = QChannel.channel
        .name.equalsIgnoreCase("TEASUN");

    // when
    Optional<Channel> optional = channelRepository.findOne(predicate);

    // then
    assert optional.get().getName().equals(newChannel.getName());
  }
}