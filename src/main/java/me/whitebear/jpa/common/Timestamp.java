package me.whitebear.jpa.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamp {

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  public void updateCreatedAt() {
    this.createdAt = LocalDateTime.now();
  }

  public void updateModifiedAt() {
    this.modifiedAt = LocalDateTime.now();
  }
}
