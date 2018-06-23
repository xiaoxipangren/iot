package com.nationalchip.iot.common.network;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/19/18 1:48 PM
 * @Modified:
 */
@Component
public class IPAware implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private int port;

    @Override
    public void onApplicationEvent(
            EmbeddedServletContainerInitializedEvent event) {
        port = event.getEmbeddedServletContainer().getPort();
    }

    public int getPort() {
        return port;
    }

    public String getHost(){
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String host = InetAddress.getLocalHost().getHostName();

            return ip+"/"+host;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

}
