package learn.spring;

import learn.base.JunitBase;
import org.junit.Test;

/**
 * for testing difference about consume queue msg between persistent or not
 *
 * @author : Lin Can
 * @date : 2019/9/25 15:51
 */
public class TestConsumeRate extends JunitBase {

    @Test
    public void test() throws Exception{
        for (int i=0; i<10; i++) {
            Thread.sleep(10000);
        }
    }
}
