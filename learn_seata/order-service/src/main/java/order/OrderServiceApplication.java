package order;


import order.mapper.OrderMapper2;
import order.model.Order;
import order.model.Order2;
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
    private OrderMapper2 orderMapper2;

    @GetMapping("order/create")
    public Boolean create(long userId , long productId){
        Order order = new Order();
        order.setCount(1)
            .setMoney(BigDecimal.valueOf(88))
            .setProductId(productId)
            .setUserId(userId)
            .setStatus(0);

        //测试传xid，是否依赖本地事务注解（ @Transactional）
        Order2 order2 = new Order2();
        BeanUtils.copyProperties(order,order2);
        order2.setUserId(order2.getUserId() + 20000);
        orderMapper2.insert(order2);

        return orderService.create(order);
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
