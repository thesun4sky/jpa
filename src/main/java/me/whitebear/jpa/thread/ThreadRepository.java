package me.whitebear.jpa.thread;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Thread.class, idClass = Long.class)
public interface ThreadRepository extends JpaRepository<Thread, Long>, ThreadRepositoryQuery {

}
