package learn.spring;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * RabbitMq管理接口
 * @author : Lin Can
 * @date : 2019/4/28 17:30
 */
public interface RabbitServiceBo {

    /**
     * 保存消息到特定的路由
     * @param exchange 交换机
     * @param routingKey 路由
     * @param object 消息内容
     */
    void sendToExchange(String exchange, String routingKey, Object object);


    /**
     * manual ack by deliveryTag
     * @param deliveryTag lastDeliveryTag
     * @param channel ack channel
     * @param multiple true to acknowledge all messages up to and
     * including the supplied delivery tag; false to acknowledge just
     * the supplied delivery tag.
     */
    void ack(Long deliveryTag, Channel channel, boolean multiple);

    /**
     * manual ack by deliveryTag
     * @param message ack message
     * @param channel ack channel
     * @param multiple true to acknowledge all messages up to and
     * including the supplied delivery tag; false to acknowledge just
     * the supplied delivery tag.
     */
    void ack(Message message, Channel channel, boolean multiple);
}
