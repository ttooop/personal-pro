package com.example.messagequeue.basicDemo;

import com.example.messagequeue.constant.RocketMQ;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author : wangyawen
 * @Description : 异步消息发送
 * @Date : 下午4:04 2021/3/13
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer=new DefaultMQProducer(RocketMQ.GroupName);

        producer.setNamesrvAddr(RocketMQ.NameSrv);

        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int msgCount=10;
        // 根据消息数量实例化倒计时计算器
        final CountDownLatch2 countDownLatch2=new CountDownLatch2(msgCount);

        for (int i = 0; i < msgCount; i++) {
            final int idx=i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg=new Message(RocketMQ.TopicName,
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            // SendCallback接收异步返回结果的回调
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n",idx,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.printf("%-10d Exception %s %n",idx,throwable);
                    throwable.printStackTrace();
                }
            });
        }
        // 等待5s
        countDownLatch2.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
