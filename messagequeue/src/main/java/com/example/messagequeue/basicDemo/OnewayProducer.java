package com.example.messagequeue.basicDemo;

import com.example.messagequeue.constant.RocketMQ;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author : wangyawen
 * @Description : 单向发送消息
 * @Date : 下午5:02 2021/3/13
 */

public class OnewayProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer=new DefaultMQProducer(RocketMQ.GroupName);

        producer.setNamesrvAddr(RocketMQ.NameSrv);

        producer.start();

        for (int i = 0; i < 10; i++) {
            Message msg=new Message(RocketMQ.TopicName,
                    "TagA",
                    ("Hello RocketMQ "+i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //发送单向消息，没有任何返回结果
            producer.sendOneway(msg);
        }
        producer.shutdown();
    }
}
