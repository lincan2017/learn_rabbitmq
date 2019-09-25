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
 * for testing difference incoming queue rate between persistent or not
 *
 * @author : Lin Can
 * @date : 2019/9/25 14:24
 */
public class TestInRate extends JunitBase {


    @Autowired
    RabbitServiceBo rabbitService;

    private Logger logger = LoggerFactory.getLogger(TestRabbitService.class);

    @Test
    public void testPersistentInRate() {
        int count = 8000;
        long begin;
        long end;
        int size = 20;

        List<Long> persistent_times = new ArrayList<>(size);
        for (int i =0; i<size; i++) {
            begin = System.nanoTime();
            for (int j=0; j<count; j++) {
                rabbitService.sendToRabbit(RabbitMqContant.DIRECT_EX, RabbitMqContant.SMS_R_KEY,
                        "I am a p msg...", MessageDeliveryMode.PERSISTENT);
            }
            end = System.nanoTime();
            persistent_times.add(end - begin);
        }

        long sum = 0;
        for (int i=0; i<size; i++) {
            sum += persistent_times.get(i);
        }
        logger.info("p_times avg {}", sum * 0.01 / size);

    }

    @Test
    public void testNonPersistentInRate() {
        int count = 8000;
        long begin;
        long end;
        int size = 20;
        List<Long> non_persistent_times = new ArrayList<>(size);

        for (int i =0; i<size; i++) {
            begin = System.nanoTime();
            for (int j=0; j<count; j++) {
                rabbitService.sendToRabbit(RabbitMqContant.DEAD_EX, RabbitMqContant.DEAD_R_KEY,
                        "I am a non_p msg...", MessageDeliveryMode.NON_PERSISTENT);
            }
            end = System.nanoTime();
            non_persistent_times.add(end - begin);
        }

        long sum = 0;
        for (int i=0; i<size; i++) {
            sum += non_persistent_times.get(i);
        }
        logger.info("non_p_times avg {}", sum * 0.01 / size);

    }
}
