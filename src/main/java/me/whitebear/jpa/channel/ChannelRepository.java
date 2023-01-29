package me.whitebear.jpa.channel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelRepository {

  @PersistenceContext
  EntityManager entityManager;


  public Channel insertChannel(Channel channel) {
    entityManager.persist(channel);
    return channel;
  }

  public Channel selectChannel(Long id) {
    return entityManager.find(Channel.class, id);
  }
}
