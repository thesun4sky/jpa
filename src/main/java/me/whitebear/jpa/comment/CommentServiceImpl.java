package me.whitebear.jpa.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  @Override
  public CommentDTO selectComment(Long commentId) {
    return commentRepository.findById(commentId)
        .map(CommentDTO::new)
        .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
  }
}
