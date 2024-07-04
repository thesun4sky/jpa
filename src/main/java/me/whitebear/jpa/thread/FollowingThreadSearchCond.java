package me.whitebear.jpa.thread;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowingThreadSearchCond {

  private Long followingUserId;
  @Default
  private SortType sortType = SortType.CREATE_AT_DESC; // 정렬 타입 (기본값: 생성일자 내림차순)

  public enum SortType {
    CREATE_AT_DESC, // 생성일자 내림차순
    USER_NAME_ASC, // 유저 이름 오름차순
  }
}
