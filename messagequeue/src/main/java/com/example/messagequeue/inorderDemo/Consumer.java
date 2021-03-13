package com.example.messagequeue.inorderDemo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午11:47 2021/3/13
 */

public class Consumer {
    /**
     * 顺序消息消费，带事务的方式（应用可控制offset什么时候提交）
     */
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        /**
         * 设置consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TestTopic", "TagA || TagC || TagD");

        consumer.registerMessageListener(new MessageListenerOrderly() {
            Random random = new Random();

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                consumeOrderlyContext.setAutoCommit(true);
                for (MessageExt msg :
                        list) {
                    //可以看到每一个queue有唯一的consumer线程来消费，订单对每个queue（分区）有序
                    System.out.println("consumerThread= " + Thread.currentThread().getName() +
                            " queueId= " + msg.getQueueId() + ", content: " + new String(msg.getBody()));
                }

                try {
                    //模拟业务逻辑处理中
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                } catch (Exception e){
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();

        System.out.println("consumer started");
    }
}
