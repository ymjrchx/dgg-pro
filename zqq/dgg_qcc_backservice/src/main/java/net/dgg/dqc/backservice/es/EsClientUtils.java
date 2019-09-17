package net.dgg.dqc.backservice.es;

import net.dgg.dqc.backservice.utils.ResourceUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;


//TODO 需要补充足够的注释说明该类的作用是什么，解决了什么问题，是如何解决的
public class EsClientUtils {
    static Log logger = LogFactory.getLog(EsClientUtils.class);

    //    用于提供单例的TransportClient BulkProcessor
    static public TransportClient tclient = null;
    static BulkProcessor staticBulkProcessor = null;

    static String ES_SERVERS = "es.servers";

    static String ES_CLUSTER_NAME = "es.cluster.name";

    static String SERVERS = "";

    static String CLUSTER_NAME = "";

    static {
        Map<String, String> configMap = ResourceUtils.getResource("application").getMap();
        SERVERS = configMap.get(ES_SERVERS);
        CLUSTER_NAME = configMap.get(ES_CLUSTER_NAME);
        
        /*SERVERS = "192.168.254.53:9300";
        CLUSTER_NAME = "dgg_es";*/
    }

    //【获取TransportClient 的方法】
    public static TransportClient getClient() {
        try {
            if (tclient != null) {
                return tclient;
            }

            Settings settings = Settings.builder()
                    .put("cluster.name", CLUSTER_NAME).put("client.transport.sniff", true).build();
            tclient = new PreBuiltTransportClient(settings);

            String[] serversArr = SERVERS.split(";");
            if (serversArr == null || serversArr.length == 0) {
                logger.info("没有配置ES服务器");
                return null;
            } else {
                for (String server : serversArr) {
                    String[] item = server.split(":");
                    InetSocketTransportAddress ista = new InetSocketTransportAddress(InetAddress.getByName(item[0]), Integer.parseInt(item[1]));
                    tclient.addTransportAddress(ista);
                }
            }
        } catch (Exception e) {
            logger.error("创建es客户端失败", e);
        }
        return tclient;
    }

    //【设置自动提交文档】
    public static BulkProcessor getBulkProcessor() {
        //自动批量提交方式
        if (staticBulkProcessor == null) {
            try {
                staticBulkProcessor = BulkProcessor.builder(getClient(),
                        new BulkProcessor.Listener() {

                            public void beforeBulk(long executionId, BulkRequest request) {
                                //提交前调用
                                logger.info("==========================================beforeBulk===========================================");
                            }

                            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                                //提交结束后调用（无论成功或失败）
                                logger.info(response.buildFailureMessage());
                                logger.info("提交" + response.getItems().length + "个文档，用时"
                                        + response.getTookInMillis() + "MS" + (response.hasFailures() ? " 有文档提交失败！" : ""));
                            }


                            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                                //提交结束且失败时调用
                                logger.error(" 有文档提交失败！after failure=" + failure);
//                                tearDown();
                            }
                        })

                        .setBulkActions(5000)//文档数量达到1000时提交
                        .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))//总文档体积达到5MB时提交 //
                        .setFlushInterval(TimeValue.timeValueSeconds(5))//每5S提交一次（无论文档数量、体积是否达到阈值）
                        .setConcurrentRequests(2)//加1后为可并行的提交请求数，即设为0代表只可1个请求并行，设为1为2个并行
                        .build();
                //                staticBulkProcessor.awaitClose(10, TimeUnit.MINUTES);//关闭，如有未提交完成的文档则等待完成，最多等待10分钟
            } catch (Exception e) {//关闭时抛出异常
                logger.error("自动提交文档异常", e);
            }
        }//if


        return staticBulkProcessor;
    }

    public static void tearDown(){
        try {
            staticBulkProcessor.awaitClose(10, TimeUnit.MINUTES);
            if (tclient != null) {
                tclient.close();
            }
        } catch (Exception e) {
            logger.error("关闭客户端异常", e);
        }

    }
}