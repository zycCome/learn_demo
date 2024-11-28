package business;


import business.client.OrderClient;
import business.client.StorageClient;
import business.mapper.BusinessMapper;
import business.model.Business;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableFeignClients
@Slf4j
public class BusinessServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }


    @Autowired
    private OrderClient orderClient;
    @Autowired
    private StorageClient storageClient;

    @Autowired
    private BusinessMapper businessMapper;

    @GetMapping("buy")
    @GlobalTransactional
    public String buy(long userId , long productId,@RequestParam(defaultValue = "1") int used) throws Exception {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}",RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        Business newRecord = new Business();
        newRecord.setMessage(userId+","+productId+","+used);
        newRecord.setVersion(0);
        businessMapper.insert(newRecord);
        try {
            orderClient.create(userId , productId);
        } catch (Exception e) {
            log.error("订单操作失败！");
            throw e;
        }
        try {
            storageClient.changeStorage(productId , used);
        } catch (Exception e) {
            log.error("库存操作失败！");
            throw e;
            // 如果不抛出异常，全局事务会提交，则之前成功的本地事务会生效，但失败的本地事务不会生效。
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }


    @GetMapping("/addBusiness")
    public boolean addBusiness(String info){
        log.info("inGlobalTransaction:{},xid:{},branchType:{}",RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        Business newRecord = new Business();
        newRecord.setMessage(info);
        newRecord.setVersion(0);
        businessMapper.insert(newRecord);
        return true;
    }

    @GetMapping("/updateBusinessById")
    @GlobalTransactional
    public boolean updateBusinessById(Long id) {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}",RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        Business business = businessMapper.selectById(id);
        if(business == null) {
            return false;
        }
        business.setMessage(business.getMessage()+",0");
        businessMapper.updateById(business);
        return true;
    }


    @GetMapping("/updateBusinessByMessage")
    @GlobalTransactional
    public boolean updateBusinessByVersion() {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}",RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        UpdateWrapper<Business> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql(true, " message = CONCAT(message, ',0') ");
        updateWrapper.eq("version",0);
        businessMapper.update(null,updateWrapper);
        return true;
    }


}
