package me.whitebear.jpa.comment;

import lombok.Data;

@Data
public class CommentDTO {

  private Long id;
  private String message;
  private Integer emotionCount;

  public CommentDTO(Comment comment) {
    this.id = comment.getId();
    this.message = comment.getMessage();
    this.emotionCount = comment.getEmotions().size();
  }
}
