
package com.zsy.domain.test;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ZookeeperTest extends BaseSpringTest {
    private ZooKeeper zooKeeper;

    private String ip = "47.100.22.19";

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private String path = "/DP/CONFIG/yuyun/zsy/TIMESTAMP";

    private Long timeStampLong=2L;

    @Test
    public void test() {
        try {
            zooKeeper = new ZooKeeper(ip, 5000, watchedEvent -> {
                if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("连接成功");
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            byte[] timeStamp=new byte[8];
            timeStamp[7]=timeStampLong.byteValue();
            zooKeeper.setData(path, timeStamp, zooKeeper.exists(path,false).getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}