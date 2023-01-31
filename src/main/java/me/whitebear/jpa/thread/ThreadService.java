package me.whitebear.jpa.thread;

import java.util.List;
import me.whitebear.jpa.channel.Channel;

public interface ThreadService {

  List<Thread> selectNotEmptyThreadList(Channel channel);

  Thread insert(Thread thread);
}
