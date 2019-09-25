package learn.spring;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;

/**
 * RabbitMq管理接口
 *
 * @author : Lin Can
 * @date : 2019/4/28 17:30
 */
public interface RabbitServiceBo {

    /**
     * 保存消息到特定的路由
     *
     * @param exchange            交换机
     * @param routingKey          路由
     * @param object              消息内容
     * @param messageDeliveryMode 是否持久化消息
     */
    void sendToRabbit(String exchange, String routingKey, Object object, MessageDeliveryMode messageDeliveryMode);

    /**
     * 将 Object 类型的对象转换为 Message 类型的对象用于与 Rabbit 交互
     *
     * @param object 待传输的数据
     * @param messageProperties 消息属性
     * @return org.springframework.amqp.core.Message
     */
    Message convertMessageIfNecessary(Object object, MessageProperties messageProperties);


    /**
     * manual ack by deliveryTag
     *
     * @param deliveryTag lastDeliveryTag
     * @param channel     ack channel
     * @param multiple    true to acknowledge all messages up to and
     *                    including the supplied delivery tag; false to acknowledge just
     *                    the supplied delivery tag.
     */
    void ack(Long deliveryTag, Channel channel, boolean multiple);

    /**
     * manual ack by deliveryTag
     *
     * @param message  ack message
     * @param channel  ack channel
     * @param multiple true to acknowledge all messages up to and
     *                 including the supplied delivery tag; false to acknowledge just
     *                 the supplied delivery tag.
     */
    void ack(Message message, Channel channel, boolean multiple);
}
