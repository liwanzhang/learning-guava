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
    public String hello2(String name) {
        return helloService.hello2(name);
    }
    public String hello3(String name) {
        return helloService.hello3(name);
    }
    public String helloWithTimeOut2(String name) {
        return helloService.helloWithTimeOut2(name);
    }



}
