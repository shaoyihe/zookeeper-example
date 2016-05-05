package com.he.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.he.IpHelper;
import org.apache.zookeeper.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by heshaoyi on 5/4/16.
 */
@Component
public class ZookeeperServer implements ApplicationContextAware, InitializingBean, DisposableBean, Watcher {

    private ApplicationContext applicationContext;
    private int port = -1;
    @Value("${zookeeper.connectString}")
    private String connectString;
    @Value("${server.register.path}")
    private String registerPath;
    @Value("${zookeeper.timeout}")
    private int connectTimeout;

    private ZooKeeper zooKeeper;
    private String path;

    @Override
    public void afterPropertiesSet() throws Exception {
        zooKeeper = new ZooKeeper(connectString, connectTimeout, this);
        new Thread(() -> {
            while (port == -1) {
                port = ((AnnotationConfigEmbeddedWebApplicationContext) applicationContext).getEmbeddedServletContainer().getPort();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            register();
        }).start();
    }

    public void register() {
        try {
            if (zooKeeper.exists("/servers", System.err::println) == null) {
                zooKeeper.create("/servers", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (zooKeeper.exists(registerPath, System.err::println) == null) {
                zooKeeper.create(registerPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            Server server = new Server("http", IpHelper.getIp(), port);
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(server);
            path = zooKeeper.create(registerPath + "/", value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.err.println("register " + value + " to " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    @Override
    public void destroy() throws Exception {
        System.err.println("close " + path);
        zooKeeper.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.Disconnected){
            try {
                afterPropertiesSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
