package study.myblog.hystrix_test.src.main.java.com.hystrix.hystrix;

package myHystrix.threadpool;

import com.netflix.hystrix.*;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by wangxindong on 2017/8/4.
 */
public class GetOrderCommand extends HystrixCommand<List> {

    OrderService orderService;

    public GetOrderCommand(String name){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ThreadPoolTestGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("testCommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(5000)
                )
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(10)   //配置队列大小
                                .withCoreSize(2)    // 配置线程池里的线程数
                )
        );
    }

    @Override
    protected List run() throws Exception {
        return orderService.getOrderList();
    }

    public static class UnitTest {
        @Test
        public void testGetOrder(){
//            new GetOrderCommand("hystrix-order").execute();
            Future<List> future =new GetOrderCommand("hystrix-order").queue();
        }

    }
}
