package com.he.controller;

import com.he.IpHelper;
import com.he.server.ZookeeperServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.SocketException;

/**
 * Created by heshaoyi on 5/4/16.
 */
@RestController
public class HelloController {

    @Autowired
    private ZookeeperServer zookeeperServer;

    @RequestMapping("/find-me")
    public String index() throws Exception {
        return "from " + IpHelper.getIp() + ":" + zookeeperServer.getPort();
    }

}