package com.mark.learn;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * Hello world! 创建节点，使用同步(sync)接口
 */
public class Zookeeper_Create_API_Sync_Usage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}

	public static void main(String[] args) throws Exception {
		ZooKeeper zookeeper = new ZooKeeper("127.1:2181", 5000, new Zookeeper_Create_API_Sync_Usage());
		connectedSemaphore.await();
		String path1 = zookeeper.create("/zk-test-ephemeral-",
										"".getBytes(),
										Ids.OPEN_ACL_UNSAFE,
										CreateMode.EPHEMERAL);
		System.out.println("Success create znode:" + path1);
		
		String path2 = zookeeper.create("/zk-test-ephemeral-",
				"".getBytes(),
				Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("Success create znode:" + path2);
	}

}

/* 输出结果：
Receive watched event:WatchedEvent state:SyncConnected type:None path:null
Success create znode:/zk-test-ephemeral-
Success create znode:/zk-test-ephemeral-0000000003
 */
