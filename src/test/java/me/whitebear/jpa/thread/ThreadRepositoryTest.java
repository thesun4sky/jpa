package me.whitebear.jpa.thread;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.channel.ChannelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ThreadRepositoryTest {

  @Autowired
  private ThreadRepository threadRepository;

  @Autowired
  private ChannelRepository channelRepository;

  @Test
  void insertSelectThreadTest() {
    // given
    var newChannel = Channel.builder().name("new-channel").build();
    var newThread = Thread.builder().message("new-message").build();
    var newThread2 = Thread.builder().message("new-message2").build();
    newThread.setChannel(newChannel);
    newThread2.setChannel(newChannel);

    // when
    var savedChannel = channelRepository.insertChannel(newChannel);
    var savedThread = threadRepository.insertThread(newThread);
    var savedThread2 = threadRepository.insertThread(newThread2);

    // then
    var foundChannel = channelRepository.selectChannel(savedChannel.getId());
    assert foundChannel.getThreads().containsAll(Set.of(savedThread, savedThread2));
  }


  @Test
  void deleteThreadByOrphanRemovalTest() {
    // given
    var newChannel = Channel.builder().name("new-channel").build();
    var newThread = Thread.builder().message("new-message").build();
    var newThread2 = Thread.builder().message("new-message2").build();
    newThread.setChannel(newChannel);
    newThread2.setChannel(newChannel);
    var savedChannel = channelRepository.insertChannel(newChannel);
    var savedThread = threadRepository.insertThread(newThread);
    var savedThread2 = threadRepository.insertThread(newThread2);

    // when
    var foundChannel = channelRepository.selectChannel(savedChannel.getId());
    foundChannel.getThreads().remove(savedThread);

    // then
    //     delete
    //    from
    //        thread
    //    where
    //        id=?
  }


}