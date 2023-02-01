package me.whitebear.jpa.user;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.whitebear.jpa.mention.CommentMention;
import me.whitebear.jpa.mention.ThreadMention;
import me.whitebear.jpa.userChannel.UserChannel;
import org.hibernate.annotations.DynamicInsert;

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// jpa
@Entity
@Table(name = "users")
@DynamicInsert
public class User {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(length = 25)
  private String username;

  @Column(length = 25)
  private String password;

  private String profileImageUrl;

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  @Builder
  public User(String username, String password, String profileImageUrl) {
    this.username = username;
    this.password = password;
    this.profileImageUrl = profileImageUrl;
  }


  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  Set<UserChannel> userChannels = new LinkedHashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  Set<CommentMention> commentMentions = new LinkedHashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  Set<ThreadMention> threadMentions = new LinkedHashSet<>();

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
  public void updatePassword(String password) {
    this.password = password;
  }
}
