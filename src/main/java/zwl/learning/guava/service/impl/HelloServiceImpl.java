package zwl.learning.guava.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import zwl.learning.guava.rpc.HelloRpcService;
import zwl.learning.guava.service.HelloService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
        List<String> re = new ArrayList<>(1);
        // 创建线程池
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));
        // 1.查询余额系统
        ListenableFuture<String> balanceFuture = pool.submit(() -> helloRpcService.hello(name));
        Futures.addCallback(balanceFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("BooleanTask: " + result);
                re.add(result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.err.println("BooleanTask: " + "failure");
                t.printStackTrace();
                re.add("failure1");
            }
        });

        // 1.查询余额系统
        ListenableFuture<String> balanceFuture2 = pool.submit(() -> helloRpcService.hello(name+2));
        Futures.addCallback(balanceFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("BooleanTask: " + result);
                re.add(result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.err.println("BooleanTask: " + "failure");
                t.printStackTrace();
                re.add("failure1");
            }
        });


        ListenableFuture<List<Object>> successfulQueries = Futures.successfulAsList(balanceFuture, balanceFuture2);

        try {
            List<Object> l = successfulQueries.get();
            System.out.println(JSON.toJSONString(l));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        Futures.addCallback(successfulQueries, new FutureCallback<Object>() {
//            public void onFailure(Throwable arg0) {
//                System.out.println(arg0);
//            }
//
//            public void onSuccess(Object arg0) {
//                System.out.println(arg0);
//                System.out.println("parallel:"
//                        + (System.currentTimeMillis() - start));
//            }
//        });
        return re.size() < 1 ? "failure....." : re.get(0);
    }

    public String hello2(String name) {
        List<String> re = new ArrayList<>(1);
        // 创建线程池
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        // 1.查询余额系统
        ListenableFuture<String> balanceFuture = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {

                String s = null;
                try {
                    System.out.println("service begin2");
                    s = helloRpcService.hello(name);
                    System.out.println("service end2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return s;
            }
        });
        Futures.addCallback(balanceFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("BooleanTask: " + result);
                re.add(result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.err.println("BooleanTask: " + "failure");
                t.printStackTrace();
                re.add("failure");
            }
        });

        return re.size() < 1 ? "failure....." : re.get(0);
    }

    public String hello3(String name) {
        // 创建线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {

                String s = null;
                try {
                    System.out.println("service begin2");
                    s = helloRpcService.hello(name);
                    System.out.println("service end2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return s;
            }
        });
        String re = null;
        try {
            re = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return re;
    }

    public String helloWithTimeOut(String name) {
        List<String> re = new ArrayList<>(1);
        // 创建线程池
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));
        SimpleTimeLimiter limiter = new SimpleTimeLimiter(pool);

        try {
            limiter.callWithTimeout(() -> helloRpcService.hello(name), 5, TimeUnit.SECONDS, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }

        HelloRpcService helloRpcServiceProxy = limiter.newProxy(helloRpcService, HelloRpcService.class, 2, TimeUnit.SECONDS);
        // 1.查询余额系统
        ListenableFuture<String> balanceFuture = pool.submit(() -> helloRpcServiceProxy.hello(name));
        Futures.addCallback(balanceFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("BooleanTask: " + result);
                re.add(result);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                System.err.println("BooleanTask: " + "failure");

            }
        });

        return re.get(0);
    }
    public String helloWithTimeOut2(String name) {
        List<String> re = new ArrayList<>(1);
        // 创建线程池
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));
        SimpleTimeLimiter limiter = new SimpleTimeLimiter(pool);

        HelloRpcService helloRpcServiceProxy = limiter.newProxy(helloRpcService, HelloRpcService.class, 2, TimeUnit.SECONDS);
        // 1.查询余额系统
        ListenableFuture<String> balanceFuture = pool.submit(() -> helloRpcServiceProxy.hello(name));
        Futures.addCallback(balanceFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("BooleanTask: " + result);
                re.add(result);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                System.err.println("BooleanTask: " + "failure");

            }
        });

        return re.get(0);
    }
}
