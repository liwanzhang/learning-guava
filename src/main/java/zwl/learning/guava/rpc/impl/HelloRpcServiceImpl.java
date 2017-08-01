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
        return name;
    }
}
