package net.dgg.tmd.foundation.platform.service.es;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class EsClientService {

    static Log logger = LogFactory.getLog(EsClientService.class);

    private TransportClient client = null;

    @Value("${es.servers}")
    private String esServers;

    @Value("${es.cluster.name}")
    private String clusterName;

    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    public TransportClient getClient() {
        try {
            if (client != null) {
                return client;
            }

            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName).put("client.transport.sniff", true).build();
            client = new PreBuiltTransportClient(settings);

            String[] serversArr = esServers.split(";");
            if (serversArr == null || serversArr.length == 0) {
                logger.info("没有配置ES服务器");
                return null;
            } else {
                for (String server : serversArr) {
                    String[] item = server.split(":");
                    InetSocketTransportAddress ista = new InetSocketTransportAddress(InetAddress.getByName(item[0]), Integer.parseInt(item[1]));
                    client.addTransportAddress(ista);
                }
            }
        } catch (Exception e) {
            logger.error("创建es客户端失败", e);
        }
        return client;
    }

}