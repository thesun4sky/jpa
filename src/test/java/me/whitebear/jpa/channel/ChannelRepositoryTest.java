package me.whitebear.jpa.channel;

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
    var savedChannel = channelRepository.insertChannel(newChannel);

    // then
    var foundChannel = channelRepository.selectChannel(savedChannel.getId());
    assert foundChannel.getId().equals(savedChannel.getId());
  }

}