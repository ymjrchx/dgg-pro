package net.dgg.dqc.backservice.utils;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Created by 李程 on 2018/10/30.
 */
public class Tookit {

    public static class EsClientUtil {
        public static TransportClient getClient(String servers, String cluster) {
            TransportClient tclient;
            try {
                Settings settings = Settings.builder()
                        .put("cluster.name", cluster).put("client.transport.sniff", true).build();
                tclient = new PreBuiltTransportClient(settings);
                String[] serversArr = servers.split(";");
                if (serversArr == null || serversArr.length == 0) {
                    System.out.println("没有配置ES服务器");
                    return null;
                } else {
                    for (String server : serversArr) {
                        String[] item = server.split(":");
                        InetSocketTransportAddress ista = new InetSocketTransportAddress(InetAddress.getByName(item[0]), Integer.valueOf(item[1]));
                        tclient.addTransportAddress(ista);
                    }
                }
                return tclient;
            } catch (Exception e) {
                System.out.println("创建es客户端失败".concat(e.getMessage()));
                return null;
            }
        }
    }

}
