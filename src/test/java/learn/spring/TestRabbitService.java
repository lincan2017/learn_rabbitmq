package learn.spring;

import learn.base.JunitBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * test API of RabbitServiceBo
 *
 * @author : Lin Can
 * @date : 2019/4/29 9:25
 */
public class TestRabbitService extends JunitBase {

    @Autowired
    RabbitServiceBo rabbitService;


    @Test
    public void testMultiContainerFactory() throws Exception{
        for (int i=0; i<10000; i++) {
            rabbitService.sendToExchange(RabbitMqContant.DIRECT_EX, RabbitMqContant.SMS_R_KEY,"I am a sms...");
            rabbitService.sendToExchange(RabbitMqContant.DIRECT_EX, RabbitMqContant.EMAIL_R_KEY,"I am a email...");
            rabbitService.sendToExchange(RabbitMqContant.DEAD_EX, RabbitMqContant.DEAD_R_KEY,"I am a dead msg...");
        }

        for (int i=0; i<5; i++) {
            Thread.sleep(10000);
            System.out.println("休息了" + 10 * (i + 1) + "秒-----------------");
        }
    }

    @Test
    public void testSeveralConsume() throws Exception{

        for (int i=0; i<100000; i++) {
            rabbitService.sendToExchange(RabbitMqContant.DIRECT_EX, RabbitMqContant.EMAIL_R_KEY,"I am a email...");
        }

        for (int i=0; i<10; i++) {
            Thread.sleep(10000);
            System.out.println("休息了" + 10 * (i + 1) + "秒-----------------");
        }
    }

}
