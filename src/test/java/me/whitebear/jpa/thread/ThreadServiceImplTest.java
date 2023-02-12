package me.whitebear.jpa.thread;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.channel.Channel.Type;
import me.whitebear.jpa.channel.ChannelRepository;
import me.whitebear.jpa.comment.Comment;
import me.whitebear.jpa.comment.CommentRepository;
import me.whitebear.jpa.common.PageDTO;
import me.whitebear.jpa.mention.ThreadMention;
import me.whitebear.jpa.user.User;
import me.whitebear.jpa.user.UserRepository;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ThreadServiceImplTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ChannelRepository channelRepository;

	@Autowired
	ThreadService threadService;

	@Autowired
	CommentRepository commentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	void getMentionedThreadList() {
		// given
		User savedUser = getTestUser("1", "2");
		var newThread = Thread.builder().message("message").build();
		newThread.addMention(savedUser);
		threadService.insert(newThread);

		var newThread2 = Thread.builder().message("message2").build();
		newThread2.addMention(savedUser);
		threadService.insert(newThread2);

		// when
		// 모든 채널에서 내가 멘션된 쓰레드 목록 조회 기능
		var mentionedThreads = savedUser.getThreadMentions().stream().map(ThreadMention::getThread)
			.toList();

		// then
		assert mentionedThreads.containsAll(List.of(newThread, newThread2));
	}

	@Test
	void getNotEmptyThreadList() {
		// given
		var user = getTestUser("1", "1");
		var newChannel = Channel.builder().name("c1").type(Type.PUBLIC).build();
		var savedChannel = channelRepository.save(newChannel);
		getTestThread("message", savedChannel, user);

		Thread newThread2 = getTestThread("", savedChannel, user);

		// when
		var notEmptyThreads = threadService.selectNotEmptyThreadList(savedChannel);

		// then
		assert !notEmptyThreads.contains(newThread2);
	}

	@Test
	@Transactional(propagation = Propagation.NEVER)
	@DisplayName("전체 채널에서 내가 멘션된 쓰레드 상세정보 목록 테스트")
	void selectMentionedThreadListTest() {
		// given
		var user = getTestUser("1", "1");
		var user2 = getTestUser("2", "2");
		var user3 = getTestUser("3", "3");
		var user4 = getTestUser("3", "4");
		var newChannel = Channel.builder().name("c1").type(Type.PUBLIC).build();
		var savedChannel = channelRepository.save(newChannel);
		var thread2 = getTestThread("message2", savedChannel, user, user
			, user2, "e2", user3, "c2", user4, "ce2");
		var thread1 = getTestThread("message1", savedChannel, user2, user
			, user2, "e1", user3, "c1", user4, "ce1");

		// when
		var pageDTO = PageDTO.builder().currentPage(1).size(100).build();
		var mentionedThreadList = threadService.selectMentionedThreadList(user.getId(), pageDTO);

		// then
		assert mentionedThreadList.getTotalElements() == 2;
	}

	private User getTestUser(String username, String password) {
		var newUser = User.builder().username(username).password(password).build();
		return userRepository.save(newUser);
	}

	private Comment getTestComment(User user, String message) {
		var newComment = Comment.builder().message(message).build();
		newComment.setUser(user);
		return commentRepository.save(newComment);
	}

	private Thread getTestThread(String message, Channel savedChannel, User user) {
		var newThread = Thread.builder().message(message).build();
		newThread.setUser(user);
		newThread.setChannel(savedChannel);
		return threadService.insert(newThread);
	}

	private Thread getTestThread(String message, Channel channel, User author, User mentionedUser) {
		var newThread = getTestThread(message, channel, author);
		newThread.addMention(mentionedUser);
		return threadService.insert(newThread);
	}

	private Thread getTestThread(String message, Channel channel, User author, User mentionedUser,
		User emotionUser, String emotionValue) {
		var newThread = getTestThread(message, channel, author, mentionedUser);
		newThread.addEmotion(emotionUser, emotionValue);
		return threadService.insert(newThread);
	}

	private Thread getTestThread(String message, Channel channel, User author, User mentionedUser,
		User emotionUser, String emotionValue, User commentUser, String commentMessage) {
		var newThread = getTestThread(message, channel, author, mentionedUser, emotionUser, emotionValue);
		newThread.addComment(getTestComment(commentUser, commentMessage));
		return threadService.insert(newThread);
	}

	private Thread getTestThread(String message, Channel channel, User author, User mentionedUser,
		User emotionUser, String emotionValue, User commentUser, String commentMessage,
		User commentEmotionUser, String commentEmotionValue) {
		var newThread = getTestThread(message, channel, author, mentionedUser, emotionUser, emotionValue,
			commentUser, commentMessage);
		newThread.getComments()
			.forEach(comment -> comment.addEmotion(commentEmotionUser, commentEmotionValue));
		return threadService.insert(newThread);
	}
}