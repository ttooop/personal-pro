package com.example.messagequeue.basicDemo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午8:53 2021/3/13
 */

public class CreateTopic {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer=new DefaultMQProducer("test");

        producer.setNamesrvAddr("127.0.0.1:9876");

        producer.start();

        producer.createTopic("broker-a","TestTopic",16);

        System.out.println("success");

        producer.shutdown();
    }
}
