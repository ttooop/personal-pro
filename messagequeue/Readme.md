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
`basicDemo.Consumer`

## 顺序消息样例
消息有序指的是可以按照消息的发送顺序来消费(FIFO)。RocketMQ可以严格的保证消息有序，可以分为分区有序或者全局有序。

顺序消费的原理解析，在默认的情况下消息发送会采取Round Robin轮询方式把消息发送到不同的queue(分区队列)；而消费消息的时候从多个queue上拉取消息，这种情况发送和消费是不能保证顺序。但是如果控制发送的顺序消息只依次发送到同一个queue中，消费的时候只从这个queue上依次拉取，则就保证了顺序。当发送和消费参与的queue只有一个，则是全局有序；如果多个queue参与，则为分区有序，即相对每个queue，消息都是有序的。

下面用订单进行分区有序的示例。一个订单的顺序流程是：创建、付款、推送、完成。订单号相同的消息会被先后发送到同一个队列中，消费时，同一个OrderId获取到的肯定是同一个队列。


## 参考网址：
https://github.com/apache/rocketmq/blob/master/docs/cn/RocketMQ_Example.md