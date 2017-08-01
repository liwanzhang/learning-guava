package zwl.learning.guava.service.impl;

import com.google.common.util.concurrent.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import zwl.learning.guava.rpc.HelloRpcService;
import zwl.learning.guava.service.HelloService;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author zhangwanli
 * @description
 * @date 2017-07-31 下午7:30
 */
@org.springframework.stereotype.Service
public class HelloServiceImpl implements HelloService{

    @Autowired
    private HelloRpcService helloRpcService;

    public String hello(String name) {
        // 创建线程池
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));
        // 1.查询余额系统
        ListenableFuture<String> balanceFuture = pool.submit(() -> helloRpcService.hello(name));
        Futures.addCallback(balanceFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("BooleanTask: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        return null;
    }
}
