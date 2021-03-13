package com.example.messagequeue.basicDemo;

import com.example.messagequeue.constant.RocketMQ;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author : wangyawen
 * @Description : 发送同步消息
 * @Date : 下午2:58 2021/3/13
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        System.out.println(RocketMQ.GroupName);
        //实例化消息生产者producer
        DefaultMQProducer producer = new DefaultMQProducer(RocketMQ.GroupName);
        //设置nameserver地址
        producer.setNamesrvAddr(RocketMQ.NameSrv);
        //启动producer实例
        producer.start();

        for (int i = 0; i < 10; i++) {
            //创建消息，并指定topic、tag和消息体
            Message msg = new Message(RocketMQ.TopicName,
                    "TagA",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //发送消息到一个broker
            SendResult sendResult = producer.send(msg);
            //通过sendresult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
