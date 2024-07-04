package me.whitebear.jpa.emotion;

import java.util.List;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.channel.ChannelRepository;
import me.whitebear.jpa.configuration.JPAConfiguration;
import me.whitebear.jpa.thread.Thread;
import me.whitebear.jpa.thread.ThreadRepository;
import me.whitebear.jpa.user.User;
import me.whitebear.jpa.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfiguration.class)
class ThreadEmotionRepositoryTest {

  @Autowired
  ChannelRepository channelRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ThreadRepository threadRepository;

  @Autowired
  ThreadEmotionRepository threadEmotionRepository;

  Channel testChannel;
  User testUser;
  Thread testThread;

  private static final String TEST_USER_NAME = "testUser";
  private static final String TEST_PASSWORD = "testPassword";
  private static final String TEST_CHANNEL_NAME = "testChannel";
  private static final String TEST_THREAD_MESSAGE = "testMessage";
  private static final String TEST_BODY = "testBody";

  @BeforeEach
  void setUp() {
    // 채널 생성
    testChannel = Channel.builder()
        .name(TEST_CHANNEL_NAME)
        .build();
    channelRepository.save(testChannel);

    // 사용자 생성
    testUser = User.builder()
        .username(TEST_USER_NAME)
        .password(TEST_PASSWORD)
        .build();
    userRepository.save(testUser);

    // 쓰레드 생성
    testThread = Thread.builder()
        .message(TEST_THREAD_MESSAGE)
        .build();
    testThread.setUser(testUser);
    testThread.setChannel(testChannel);
    threadRepository.save(testThread);
  }

  /**
   * 사용자가 좋아요한 쓰레드 목록을 조회합니다.
   */
  @Test
  void findLikedThreads() {
    // given - 쓰레드 좋아요 생성
    var testThreadEmotions = List.of(ThreadEmotion.builder()
            .user(testUser)
            .thread(testThread)
            .body(TEST_BODY)
            .build(),
        ThreadEmotion.builder()
            .user(testUser)
            .thread(testThread)
            .body(TEST_BODY)
            .build());
    threadEmotionRepository.saveAll(testThreadEmotions);

    // when - 사용자가 좋아요한 쓰레드 목록 조회
    var likedThreads = threadEmotionRepository.findLikedThreads(testUser, Pageable.ofSize(5));

    // then - 사용자가 좋아요한 쓰레드 목록 사이즈 2
    assert likedThreads.getTotalElements() == 2;
  }

  /**
   * 사용자가 작성한 쓰레드 수를 조회합니다.
   */
  @Test
  void getCountLikedThreads() {
    // given - 쓰레드 좋아요 2개 생성
    var testThreadEmotions = List.of(ThreadEmotion.builder()
            .user(testUser)
            .thread(testThread)
            .body(TEST_BODY)
            .build(),
        ThreadEmotion.builder()
            .user(testUser)
            .thread(testThread)
            .body(TEST_BODY)
            .build());
    threadEmotionRepository.saveAll(testThreadEmotions);

    // when - 사용자가 좋아요 한 쓰레드 수 조회
    var countLikedThreads = threadEmotionRepository.getCountLikedThreads(testUser);

    // then - 사용자가 좋아요 한 쓰레드 수 2
    assert countLikedThreads == 2;
  }
}