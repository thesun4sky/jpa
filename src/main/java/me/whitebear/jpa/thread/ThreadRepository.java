package me.whitebear.jpa.thread;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadRepository {

  @PersistenceContext
  EntityManager entityManager;


  public Thread insertThread(Thread thread) {
    entityManager.persist(thread);
    return thread;
  }

  public Thread selectThread(Long id) {
    return entityManager.find(Thread.class, id);
  }
}
