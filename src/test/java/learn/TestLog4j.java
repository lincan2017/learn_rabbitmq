package learn;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * test log4j config
 *
 * @author : Lin Can
 * @date : 2019/9/23 15:50
 */
public class TestLog4j {

    private Logger logger = LoggerFactory.getLogger(TestLog4j.class);
    @Test
    public void testErrorLog() {
        logger.error("test error!!!");
    }

    @Test
    public void testInfoLog() {
        logger.info("test info!!!");
    }

    @Test
    public void testWarnLog() {
        logger.info("test warn!!!");
    }
}
