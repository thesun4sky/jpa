package me.whitebear.jpa.thread;

import java.util.List;
import me.whitebear.jpa.channel.Channel;
import me.whitebear.jpa.common.PageDTO;
import org.springframework.data.domain.Page;

public interface ThreadService {

  List<Thread> selectNotEmptyThreadList(Channel channel);

  Page<Thread> selectMentionedThreadList(Long userId, PageDTO pageDTO);

  Thread insert(Thread thread);
}
