package com.example.messagequeue.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author : wangyawen
 * @Description :
 * @Date : 下午3:33 2021/3/13
 */
@Configuration
public class RocketMQ {


    public static String GroupName = "test";


    public static String NameSrv = "127.0.0.1:9876";


    public static String TopicName = "TestTopic";

    @Value("${rocketmq.group-name}")
    public static void setGroupName(String groupName) {
        GroupName = groupName;
    }

    @Value("${rocketmq.namesrv}")
    public static void setNameSrv(String nameSrv) {
        NameSrv = nameSrv;
    }

    @Value("${rocketmq.topicname}")
    public static void setTopicName(String topicName) {
        TopicName = topicName;
    }
}
