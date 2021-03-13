package com.example.messagequeue.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Component;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午11:06 2021/1/29
 */
@Component
public class Producer {

    //生产者所属组
    private String producerGroup = "test_group";

    //mq的服务地址
    private String nameSrvAddr = "127.0.0.1:9876";
    private DefaultMQProducer producer;

    public Producer() {
        //组
        producer = new DefaultMQProducer(producerGroup);
        //制定nameserver的地址，集群地址以;隔开
        producer.setNamesrvAddr(nameSrvAddr);
        start();
    }

    /**
     * 获取生产者
     *
     * @return
     */
    public DefaultMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 开启,对象在使用前要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭
     */
    public void shutdown() {
        this.producer.shutdown();
    }

}
