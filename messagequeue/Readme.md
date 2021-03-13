# RocketMQ demo
## 1 基本样例
```
- 使用RocketMQ实现三种类型消息的发送：同步消息、异步消息和单向消息。其中前两种消息是可靠的，因为会有发送是否成功的应答。
- 消费接收到的消息。
```
### 1.1 加入依赖
```xml
<!--rocketMQ 依赖: 这里的version与服务保持一致-->
        <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-client</artifactId>
<!--            <version>4.2.0</version>-->
            <version>4.4.0</version>
        </dependency>
```

### 1.2 消息发送
1. Producer发送同步消息：

    `SyncProducer`——可靠，一般用于发送重要的消息通知、短信通知
2. 发送异步消息：

    `AsyncProducer`——异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间的等待Brocker的相应
    
3. 单向发送消息：

    `OnewayProducer`——这种方式主要用在不关心发送结果的场景，例如日志的发送

### 1.3 消费消息


## 参考网址：
https://github.com/apache/rocketmq/blob/master/docs/cn/RocketMQ_Example.md