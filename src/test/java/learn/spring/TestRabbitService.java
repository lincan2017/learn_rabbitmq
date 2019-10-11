package learn.spring;

import learn.base.JunitBase;
import org.junit.Test;
import org.springframework.amqp.core.MessageDeliveryMode;
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
            rabbitService.sendToRabbit(RabbitMqConstant.DIRECT_EX, RabbitMqConstant.SMS_R_KEY,"I am a sms...", MessageDeliveryMode.NON_PERSISTENT);
            rabbitService.sendToRabbit(RabbitMqConstant.DIRECT_EX, RabbitMqConstant.EMAIL_R_KEY,"I am a email...", MessageDeliveryMode.NON_PERSISTENT);
            rabbitService.sendToRabbit(RabbitMqConstant.DEAD_EX, RabbitMqConstant.DEAD_R_KEY,"I am a dead msg...", MessageDeliveryMode.NON_PERSISTENT);
        }

        for (int i=0; i<5; i++) {
            Thread.sleep(10000);
            System.out.println("休息了" + 10 * (i + 1) + "秒-----------------");
        }
    }

    @Test
    public void testSeveralConsume() throws Exception{

        for (int i=0; i<100000; i++) {
            rabbitService.sendToRabbit(RabbitMqConstant.DIRECT_EX, RabbitMqConstant.EMAIL_R_KEY,"I am a email...", MessageDeliveryMode.NON_PERSISTENT);
        }

        for (int i=0; i<10; i++) {
            Thread.sleep(10000);
            System.out.println("休息了" + 10 * (i + 1) + "秒-----------------");
        }
    }

    @Test
    public void testPersistentMsg() {
        for (int i=0; i<10; i++) {
            rabbitService.sendToRabbit(RabbitMqConstant.DIRECT_EX, RabbitMqConstant.SMS_R_KEY, "I am a sms...", MessageDeliveryMode.NON_PERSISTENT);
            rabbitService.sendToRabbit(RabbitMqConstant.DIRECT_EX, RabbitMqConstant.SMS_R_KEY, "I am a sms...", MessageDeliveryMode.PERSISTENT);
        }
    }

    /**
     * 用于测试不用的ack方式对效率的影响
     * @throws Exception
     */
    @Test
    public void testDiffAck() throws Exception{
        /*for (int i=0; i<50000; i++) {
            rabbitService.sendToRabbit(RabbitMqConstant.DIRECT_EX, RabbitMqConstant.SMS_R_KEY,"I am a sms...", MessageDeliveryMode.NON_PERSISTENT);
            rabbitService.sendToRabbit(RabbitMqConstant.DEAD_EX, RabbitMqConstant.DEAD_R_KEY,"I am a dead msg...", MessageDeliveryMode.NON_PERSISTENT);
        }*/
        for (int i=0; i<5; i++) {
            Thread.sleep(10000);
            System.out.println("休息了" + 10 * (i + 1) + "秒-----------------");
        }
    }

    @Test
    public void testBlocking() throws Exception{
        for (int i=0; i<5; i++) {
            rabbitService.sendToRabbit(RabbitMqConstant.DIRECT_EX, RabbitMqConstant.SMS_R_KEY,"I am a sms...", MessageDeliveryMode.NON_PERSISTENT);
            rabbitService.sendToRabbit(RabbitMqConstant.DEAD_EX, RabbitMqConstant.DEAD_R_KEY,"I am a dead msg...", MessageDeliveryMode.NON_PERSISTENT);
        }

        for (int i=0; i<9; i++) {
            Thread.sleep(10000);
            System.out.println("休息了" + 10 * (i + 1) + "秒-----------------");
        }
    }


}
