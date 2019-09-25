package learn.spring.listener;

import com.rabbitmq.client.Channel;
import learn.spring.RabbitMqContant;
import learn.spring.RabbitServiceBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  mq listeners
 *
 * @author : Lin Can
 * @date : 2019/4/29 23:15
 */
@Component
public class MyMqListener {

    private Logger logger = LoggerFactory.getLogger(MyMqListener.class);

    private final RabbitServiceBo rabbitServiceBo;

    @Autowired
    public MyMqListener(RabbitServiceBo rabbitServiceBo) {
        this.rabbitServiceBo = rabbitServiceBo;
    }

    //@RabbitListener(queues = RabbitMqContant.SMS_QUEUE, containerFactory = "manualAckContainerFactory")
    public void consumeSms(Message message, Channel channel) {
        logger.info("Sms : {}", new String(message.getBody()));

        rabbitServiceBo.ack(message,channel,false);
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@RabbitListener(queues = RabbitMqContant.EMAIL_QUEUE, containerFactory = "manualAckContainerFactory")
    public void consumeEmail(Message message, Channel channel) {
        logger.info("Email : {}", new String(message.getBody()));
    }

    //@RabbitListener(queues = RabbitMqContant.DEAD_QUEUE, containerFactory = "autoAckContainerFactory")
    public void consumeDead(Message message, Channel channel) {
        logger.info("Dead : {}", new String(message.getBody()));
        rabbitServiceBo.ack(message,channel, true);
    }


}
