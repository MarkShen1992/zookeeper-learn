package com.mark.learn;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * Hello world! 创建一个最基本的ZooKeeper会话实例， 复用sessionId和session passwd
 */
public class Zookeeper_Constructor_Usage_With_SID_PASSWD implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}

	public static void main(String[] args) throws Exception {
		ZooKeeper zookeeper = new ZooKeeper("127.1:2181", 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD());
		long sessionId = zookeeper.getSessionId();
		byte[] passwd = zookeeper.getSessionPasswd();
		connectedSemaphore.await();
		// illegal sessionId and sessionPassWd
		ZooKeeper zk = new ZooKeeper("127.1:2181", 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD(), 1L,
				"test".getBytes());
		
		// correct sessionId and sessionPassWd
		ZooKeeper zk2 = new ZooKeeper("127.1:2181", 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD(), sessionId,
				passwd);
		Thread.sleep(Integer.MAX_VALUE);
	}

}

/* 输出结果：
Receive watched event:WatchedEvent state:SyncConnected type:None path:null
Receive watched event:WatchedEvent state:Expired type:None path:null
Receive watched event:WatchedEvent state:SyncConnected type:None path:null
 */
