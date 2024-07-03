package me.whitebear.jpa.user;

import lombok.RequiredArgsConstructor;
import me.whitebear.jpa.emotion.EmotionService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final EmotionService emotionService;

  @Override
  public UserDTO getUserProfile(Long userId) {
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    
    // 사용자가 작성한 쓰레드 감정과 댓글 감정 수를 조회합니다.
    var threadEmotionCount = emotionService.getCountLikedThreads(user);
    var commentEmotionCount = emotionService.getCountLikedComments(user);

    return UserDTO.builder()
        .user(user)
        .threadEmotionCount(threadEmotionCount)
        .commentEmotionCount(commentEmotionCount)
        .build();
  }
}
