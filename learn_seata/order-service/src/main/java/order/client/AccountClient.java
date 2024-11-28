package order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zyc66
 * @date 2024/11/27 15:39
 **/
@FeignClient(name = "account-service")
@Component
public interface AccountClient {


    @PostMapping("update")
    public Boolean update(@RequestParam("userId")Long userId ,@RequestParam("money")  int money);

}
