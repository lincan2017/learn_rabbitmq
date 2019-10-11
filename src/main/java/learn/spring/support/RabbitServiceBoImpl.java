package learn.spring.support;

import com.rabbitmq.client.Channel;
import learn.spring.RabbitServiceBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
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

    private static final MessageProperties messagePropertiesNonPersistent;

    private static final MessageConverter DEFAULT_MESSAGE_CONVERTER = new SimpleMessageConverter();

    static {
        messagePropertiesNonPersistent = new MessageProperties();
        messagePropertiesNonPersistent.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
    }

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitServiceBoImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void test() {

    }

    @Override
    public void sendToRabbit (String exchange, String routingKey, Object object, MessageDeliveryMode messageDeliveryMode) {

        if (messageDeliveryMode.equals(MessageDeliveryMode.NON_PERSISTENT)) {
            Message message = convertMessageIfNecessary(object, messagePropertiesNonPersistent);
            rabbitTemplate.send(exchange, routingKey,message);
        } else {
            rabbitTemplate.convertAndSend(exchange, routingKey, object);
        }
    }

    @Override
    public Message convertMessageIfNecessary(Object object, MessageProperties messageProperties) {
        if (object instanceof Message) {
            return (Message) object;
        }
        return DEFAULT_MESSAGE_CONVERTER.toMessage(object, messageProperties);
    }


    /**
     * 手动回复的处理方法
     *
     * @param deliveryTag 消息传递的唯一标识
     * @param channel 信道
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
