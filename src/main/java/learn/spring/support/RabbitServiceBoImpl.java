package learn.spring.support;

import com.rabbitmq.client.Channel;
import learn.spring.RabbitServiceBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * RabbitMq管理接口实现类
 *
 * @author : Lin Can
 * @date : 2019/4/28 17:34
 */
@Service
public class RabbitServiceBoImpl implements RabbitServiceBo {

    private Logger logger = LoggerFactory.getLogger(RabbitServiceBoImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Channel getChannel(boolean transactional) {
        return rabbitTemplate.getConnectionFactory().createConnection().createChannel(transactional);
    }

    @Override
    public void sendToExchange(String exchange, String routingKey, Object object) {
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }


    /**
     * 手动回复的处理方法
     *
     * @param deliveryTag
     * @param channel
     */
    @Override
    public void ack(Long deliveryTag, Channel channel, boolean multiple) {
        if (deliveryTag == null || channel == null) {
            return;
        }
        logger.debug("ack channel:{}",channel.getChannelNumber());
        try {
            channel.basicAck(deliveryTag, multiple);
        } catch (IOException e) {
            logger.error(
                    String.format("manual ack fail, deliveryTag:{%d}", deliveryTag),
                    e);
        }
    }

    /**
     * manual ack
     * @param message ack message
     * @param channel ack channel
     */
    @Override
    public void ack(Message message, Channel channel, boolean multiple) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),multiple);
        } catch (IOException e) {
            logger.error(
                    String.format("manual ack fail, message:{%s}", new String(message.getBody())),
                    e);
        }
    }



}
