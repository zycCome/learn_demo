package business;


import business.client.OrderClient;
import business.client.StorageClient;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@SpringBootApplication
@RestController
@EnableFeignClients
@EnableDiscoveryClient
@Slf4j
public class BusinessServiceApplication {

    @Autowired
    private OrderClient orderClient;
    @Autowired
    private StorageClient storageClient;

    @GetMapping("buy")
    @GlobalTransactional
    public String buy(long userId , long productId){
        orderClient.create(userId , productId);
        try {
            storageClient.changeStorage(userId , 1);
        } catch (Exception e) {
            log.error("库存操作失败！");
        }
        return "ok";
    }

    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }

}