package me.whitebear.jpa.channel;

import jakarta.persistence.*;
import lombok.*;
import me.whitebear.jpa.common.Timestamp;
import me.whitebear.jpa.thread.Thread;
import me.whitebear.jpa.user.User;
import me.whitebear.jpa.userChannel.UserChannel;

import java.util.LinkedHashSet;
import java.util.Set;

@ToString

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// jpa
@Entity
public class Channel extends Timestamp {

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        PUBLIC, PRIVATE
    }

    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */
    @Builder
    public Channel(String name, Type type) {
        this.name = name;
        this.type = type;
    }


    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Thread> threads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChannel> userChannels = new LinkedHashSet<>();

    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */
    public UserChannel joinUser(User user) {
        var userChannel = UserChannel.builder().user(user).channel(this).build();
        this.userChannels.add(userChannel);
        user.getUserChannels().add(userChannel);
        return userChannel;
    }

    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */
    public void addThread(Thread thread) {
        this.threads.add(thread);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateId(Long id) {
        this.id = id;
    }
}
