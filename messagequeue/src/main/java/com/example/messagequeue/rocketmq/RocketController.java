package com.example.messagequeue.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午11:35 2021/1/29
 */
@RestController
@RequestMapping("/mq/rocket")
public class RocketController {
    @Autowired
    private Producer producer;

    private static final String TOPIC = "test-topic";

    @RequestMapping("test")
    public Object callback(String text) {
        Message message = new Message(TOPIC, "tagA", ("hello rocket ===> " + text).getBytes());
        try {
            SendResult sendResult = producer.getProducer().send(message);
            System.out.println("send success ====> " + sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    public static void main(String[] args) {
        DefaultMQProducer producer1 = new DefaultMQProducer("test_group");
        producer1.setNamesrvAddr("localhost:9876");
        try {
            producer1.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        Message message = new Message(TOPIC, "tagA", ("hello rocket ===> " + "text text 1").getBytes());
        try {
            SendResult sendResult = producer1.send(message);
            System.out.println("send success ====> " + sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
