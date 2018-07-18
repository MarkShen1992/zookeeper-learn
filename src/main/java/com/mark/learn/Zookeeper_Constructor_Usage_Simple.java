package com.mark.learn;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * Hello world!
 * 创建一个最基本的ZooKeeper会话实例
 */
public class Zookeeper_Constructor_Usage_Simple implements Watcher {
	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    
	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}
	
	public static void main( String[] args ) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper("127.1:2181", 
        									5000,
        									new Zookeeper_Constructor_Usage_Simple());
        System.out.println(zookeeper.getState());
        try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			
		}
        System.out.println("Zookeeper session established.");
    }

}

/* 输出结果：
CONNECTING
Receive watched event:WatchedEvent state:SyncConnected type:None path:null
Zookeeper session established.
 */
