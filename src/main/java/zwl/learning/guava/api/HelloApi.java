package zwl.learning.guava.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zwl.learning.guava.service.HelloService;

/**
 * @author zhangwanli
 * @description
 * @date 2017-07-31 下午7:27
 */
@Service
public class HelloApi {
    @Autowired
    private HelloService helloService;

    public String hello(String name) {
        return helloService.hello(name);
    }
}
