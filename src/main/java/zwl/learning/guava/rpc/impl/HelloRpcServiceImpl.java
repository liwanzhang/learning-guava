package zwl.learning.guava.rpc.impl;

import org.springframework.stereotype.Service;
import zwl.learning.guava.rpc.HelloRpcService;

/**
 * @author zhangwanli
 * @description
 * @date 2017-07-31 下午7:55
 */
@Service
public class HelloRpcServiceImpl implements HelloRpcService {
    public String hello(String name) {
        try {
            System.out.println("begin");
            for (Long l = 0L; l < 100000000L; l++) {
//                System.out.println(l);
            }
            Thread.sleep(10000L);
            System.out.println("sleep over");
            throw new NullPointerException("is null pointer");
        } catch (Exception e) {
//            e.printStackTrace();
            throw new NullPointerException("is null pointer");
        }
//        System.out.println("rpc return");
//        return name;
    }
}
