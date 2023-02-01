package me.whitebear.jpa.thread;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadSearchCond {

  private Long channelId;
  private Long mentionedUserId; // 멘션된 유저 ID
}
