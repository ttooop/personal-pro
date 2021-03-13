package com.example.messagequeue.basicDemo;

import com.example.messagequeue.constant.RocketMQ;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午5:40 2021/3/13
 */
@Component
public class Consumer {
    public static void main(String[] args) throws Exception{
        //实例化消费者
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(RocketMQ.GroupName);

        //设置nameserver的地址
        consumer.setNamesrvAddr(RocketMQ.NameSrv);
        //订阅一个或者多个topic，以及tag来过滤需要消费的消息
        consumer.subscribe(RocketMQ.TopicName,"*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.printf("%s received new messages: %s %n",Thread.currentThread().getName(),list);
                //标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("consumer started");
    }
}
