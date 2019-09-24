package learn.spring;

import learn.base.JunitBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * test mq error handler
 *
 * @author : Lin Can
 * @date : 2019/9/24 14:31
 */
public class TestErrorHandler extends JunitBase {

    @Autowired
    private RabbitServiceBo rabbitServiceBo;

    @Test
    public void testDefaultErrorHandler() throws Exception{
        for (int i=0; i<10000; i++) {
            rabbitServiceBo.sendToExchange(RabbitMqContant.DEAD_EX, RabbitMqContant.DEAD_R_KEY,"I am a dead msg...");
        }

        for (int i=0; i<8; i++) {
            Thread.sleep(10000);
            System.out.println("休息了" + 10 * (i + 1) + "秒-----------------");
        }

        /*
        测试结果，mqListener相关的异常，用默认的ErrorHandler也可以记录到log中
         */
    }
}
