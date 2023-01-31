package me.whitebear.jpa.thread;

import java.util.List;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.channel.Channel.Type;
import me.whitebear.jpa.channel.ChannelRepository;
import me.whitebear.jpa.mention.Mention;
import me.whitebear.jpa.user.User;
import me.whitebear.jpa.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThreadServiceImplTest {

  @Autowired
  UserRepository userRepository;


  @Autowired
  ChannelRepository channelRepository;

  @Autowired
  ThreadService threadService;

  @Test
  void getMentionedThreadList() {
    // given
    var newUser = User.builder().username("new").password("1").build();
    var savedUser = userRepository.save(newUser);
    var newThread = Thread.builder().message("message").build();
    newThread.addMention(savedUser);
    threadService.insert(newThread);

    var newThread2 = Thread.builder().message("message2").build();
    newThread2.addMention(savedUser);
    threadService.insert(newThread2);

    // when
    // 모든 채널에서 내가 멘션된 쓰레드 목록 조회 기능
    var mentionedThreads = savedUser.getMentions().stream().map(Mention::getThread).toList();

    // then
    assert mentionedThreads.containsAll(List.of(newThread, newThread2));
  }

  @Test
  void getNotEmptyThreadList() {
    // given
    var newChannel = Channel.builder().name("c1").type(Type.PUBLIC).build();
    var savedChannel = channelRepository.save(newChannel);
    var newThread = Thread.builder().message("message").build();
    newThread.setChannel(savedChannel);
    threadService.insert(newThread);

    var newThread2 = Thread.builder().message("").build();
    newThread2.setChannel(savedChannel);
    threadService.insert(newThread2);

    // when
    var notEmptyThreads = threadService.selectNotEmptyThreadList(savedChannel);

    // then
    assert !notEmptyThreads.contains(newThread2);
  }
}