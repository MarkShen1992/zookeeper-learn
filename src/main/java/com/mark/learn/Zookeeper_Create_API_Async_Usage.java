package com.mark.learn;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * Hello world! 创建节点，使用异步(async)接口
 */
public class Zookeeper_Create_API_Async_Usage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}

	public static void main(String[] args) throws Exception {
		ZooKeeper zookeeper = new ZooKeeper("127.1:2181", 5000, new Zookeeper_Create_API_Async_Usage());
		connectedSemaphore.await();
		zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
				new IStringCallback(), "I am context.");
		
		zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
				new IStringCallback(), "I am context.");
		
		zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
				new IStringCallback(), "I am context.");
		Thread.sleep(Integer.MAX_VALUE);
	}

}

class IStringCallback implements AsyncCallback.StringCallback {
	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("Create path result:[" + rc + ", " + path + ", " + ctx + ", real path name:" + name);
	}
}

/*
 输出结果：
Receive watched event:WatchedEvent state:SyncConnected type:None path:null
Create path result:[0, /zk-test-ephemeral-, I am context., real path name:/zk-test-ephemeral-
Create path result:[-110, /zk-test-ephemeral-, I am context., real path name:null
Create path result:[0, /zk-test-ephemeral-, I am context., real path name:/zk-test-ephemeral-0000000005
 */
