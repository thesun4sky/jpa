package me.whitebear.jpa.thread;

import lombok.Data;

@Data
public class ThreadDTO {

  private Long id;
  private String message;
  private Integer emotionCount;

  public ThreadDTO(Thread thread) {
    this.id = thread.getId();
    this.message = thread.getMessage();
    this.emotionCount = thread.getEmotions().size();
  }
}
