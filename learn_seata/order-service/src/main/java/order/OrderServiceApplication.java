package order;


import io.seata.common.util.StringUtils;
import io.seata.core.context.RootContext;
import order.client.AccountClient;
import order.model.Order;
import order.service.OrderService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("order.mapper")
public class OrderServiceApplication {

    @Autowired
    private OrderService orderService;


    @Autowired
    private AccountClient accountClient;


    @GetMapping("order/create")
    public Boolean create(long userId , long productId){
        Order order = new Order();
        order.setCount(1)
            .setMoney(BigDecimal.valueOf(88))
            .setProductId(productId)
            .setUserId(userId)
            .setStatus(0);
        if(userId == 0) {

        }
        orderService.create(order);
        if(userId == 0) {
            // 测试本地事务
            orderService.create2RecordInLocalTransactional(order);
        } else if(userId == -1) {
            orderService.create2RecordNotInLocalTransactional(order);
        } else if(userId == -2) {
            // 测试挂起全局事务，然后开启本地事务
            String xid = RootContext.getXID();
            try {
                RootContext.unbind();
                Order newOrder = new Order();
                BeanUtils.copyProperties(order,newOrder);
                orderService.create2RecordInLocalTransactional(newOrder);
            } finally {
                if(StringUtils.isNotEmpty(xid)) {
                    RootContext.bind(xid);
                }
            }
        } else if (userId == -3) {
            // 测试挂起全局事务(通过全局事务注解的方式)，然后开启本地事务
            Order newOrder = new Order();
            BeanUtils.copyProperties(order,newOrder);
            orderService.create2RecordInLocalTransactionalAndNoGlobalTransaction(newOrder);
        }
        accountClient.update(userId,1);
        return true;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
