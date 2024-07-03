package me.whitebear.jpa.thread;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.comment.Comment;
import me.whitebear.jpa.common.Timestamp;
import me.whitebear.jpa.emotion.ThreadEmotion;
import me.whitebear.jpa.mention.ThreadMention;
import me.whitebear.jpa.user.User;

import java.util.LinkedHashSet;
import java.util.Set;

@ToString

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// jpa
@Entity
public class Thread extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    @Column(length = 500)
    private String message;

    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */
    @Builder
    public Thread(String message) {
        this.message = message;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThreadMention> mentions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThreadEmotion> emotions = new LinkedHashSet<>();

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */
  public void setChannel(Channel channel) {
    this.channel = channel;
    channel.addThread(this);
  }
    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void addMention(User user) {
        var mention = ThreadMention.builder().user(user).thread(this).build();
        this.mentions.add(mention);
        user.getThreadMentions().add(mention);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setThread(this);
    }

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
  public void updateUser(User user) {
    this.user = user;
  }

  public void addEmotion(User user, String body) {
    var emotion = ThreadEmotion.builder().user(user).thread(this).body(body).build();
    this.emotions.add(emotion);
  }

    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */

}
