package com.jx;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * @description:
 * @author: jiangxing
 * @createDate: 2022/7/24
 * @version: 1.0
 */
public class ZkLock implements Runnable {
    private int tickets = 10;
    private InterProcessMutex lock;
    public ZkLock(){
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .build();
        client.start();
        lock = new InterProcessMutex(client,"/lock");
    }

    @Override
    public void run() {
        while (true){
            try {
                try {
                    lock.acquire();
                    if (tickets > 0){
                        // 业务逻辑
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
