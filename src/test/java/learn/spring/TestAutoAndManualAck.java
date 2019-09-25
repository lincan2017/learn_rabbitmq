package learn.spring;

import learn.base.JunitBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * for testing difference consume rate between auto and manual ack
 *
 * @author : Lin Can
 * @date : 2019/9/25 14:24
 */
public class TestAutoAndManualAck extends JunitBase {


    @Autowired
    RabbitServiceBo rabbitService;

    @Test
    public void testPersistentInRate() {
        int count = 8000;
        int size = 20;
        for (int i =0; i<size * count; i++) {
            rabbitService.sendToRabbit(RabbitMqConstant.DIRECT_EX, RabbitMqConstant.SMS_R_KEY,
                    "I am a msg...", MessageDeliveryMode.NON_PERSISTENT);
            rabbitService.sendToRabbit(RabbitMqConstant.DEAD_EX, RabbitMqConstant.DEAD_R_KEY,
                    "I am a msg...", MessageDeliveryMode.NON_PERSISTENT);
        }

    }
}
