package com.he;

import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

/**
 * Created by heshaoyi on 5/4/16.
 */
public class IpHelper {
    public static String getIp() throws SocketException {
        return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
                .filter(ip -> ip instanceof Inet4Address)
                .findFirst().orElseThrow(RuntimeException::new)
                .getHostAddress();
    }

}
