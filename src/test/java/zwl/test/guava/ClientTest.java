package zwl.test.guava;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zwl.learning.guava.api.HelloApi;

/**
 * @description
 * @author zhangwanli
 * @date 2017-08-01 下午3:55
 */
@ContextConfiguration({"classpath:applicationContext-main.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientTest {

    @Autowired
    private HelloApi helloApi;

    @Test
    public void helloTest(){
        System.out.println(helloApi.hello("king"));
    }
}
