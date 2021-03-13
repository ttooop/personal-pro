package com.example.messagequeue.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午1:40 2021/1/30
 */
@Component
public class Consumer {
    private String producerGroup = "test_group";

    private String nameSrvAddr = "localhost:9876";

    private String topic = "test-topic";

    private DefaultMQPushConsumer consumer;

    public Consumer() throws MQClientException {
        consumer = new DefaultMQPushConsumer(producerGroup);

        //指定nameserver地址
        consumer.setNamesrvAddr(nameSrvAddr);
        //设置消费地点，从最后一个开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题，监听主题下的那些标签
        consumer.subscribe(topic, "*");

        consumer.start();
        System.out.println("consumer start ... ");

        //实现一个监听器
        consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
            try {
                Message message = msg.get(0);
                System.out.println(Thread.currentThread().getName() + " receive new messages: " +
                        new String(msg.get(0).getBody(), "utf-8"));

                String topic = message.getTopic();
                String body = new String(message.getBody(), "utf-8");
                String tags = message.getTags();

                String keys = message.getKeys();
                System.out.println("topic=" + topic + " ,tags= " + tags + " , keys= " + keys + " ,msg=" + body);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
    }
}
