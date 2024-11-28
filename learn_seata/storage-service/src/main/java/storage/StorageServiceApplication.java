package storage;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import storage.service.StorageService;

@RestController
@SpringBootApplication
@MapperScan("storage.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class StorageServiceApplication {

    @Autowired
    private StorageService storageService;

    @GetMapping("storage/change")
    public Boolean changeStorage(long productId , int used)  {
        return storageService.updateUseNum(productId , used);
    }

    @GetMapping("storage/prepareChange")
    public Boolean prepareChange(long productId , int used)  {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}", RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());

        return storageService.prepareUpdateUseNum(null,productId , used);
    }

    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }

}
