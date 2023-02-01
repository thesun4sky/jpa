package me.whitebear.jpa.user;

import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.channel.ChannelRepository;
import me.whitebear.jpa.configuration.JPAConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfiguration.class)
@Rollback(value = false)
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ChannelRepository channelRepository;

  @Test
  void dynamicInsertTest() {
    // given
    var newUser = User.builder().username("user").build();

    // when
    userRepository.save(newUser);

    // then
    // 부분 생성 쿼리
  }

  @Test
  void dynamicUpdateTest() {
    // given
    var newUser = User.builder().username("user").password("password").build();
    userRepository.save(newUser);

    // when
    newUser.updatePassword("new password");
    userRepository.save(newUser);

    // then
    // 부분 수정 쿼리
  }

  @Test
  void projectionTest() {
    // given
    var newUser = User.builder().username("user").profileImageUrl("http://").password("pass")
        .build();
    var newChannel = Channel.builder().name("new-channel").build();

    // when
    var savedChannel = channelRepository.save(newChannel);
    var savedUser = userRepository.save(newUser);
    var newUserChannel = newChannel.joinUser(newUser);

    // then interface projection
    var userProfiles = userRepository.findByUsername("user");
    System.out.println("interface projection : ");
    userProfiles.forEach(userProfile -> System.out.println(userProfile.hasProfileImage()));
    assert !userProfiles.isEmpty();

    // then class projection
    var userInfos = userRepository.findByPassword("pass");
    System.out.println("class projection : ");
    userInfos.forEach(userInfo -> System.out.println(userInfo.getUserInfo()));
    assert !userInfos.isEmpty();

    // then dynamic projection
    var userProfiles2 = userRepository.findByProfileImageUrlStartingWith("http", UserProfile.class);
    System.out.println("dynamic projection : ");
    userProfiles2.forEach(userInfo -> System.out.println(userInfo.getProfileImageUrl()));
    assert !userProfiles2.isEmpty();
  }

  @Test
  public void queryByExampleTest() {
    // given
    var newUser = User.builder().username("user").profileImageUrl("http://").password("pass")
        .build();
    userRepository.save(newUser);

    // when
    User prove = new User();
    prove.updatePassword("pass");
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnorePaths("up", "down");
    Example<User> example = Example.of(prove, exampleMatcher);
    var users = userRepository.findAll(example);

    // then
    assert !users.isEmpty();
  }
}
