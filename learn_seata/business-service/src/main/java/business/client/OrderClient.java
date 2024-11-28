package business.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@FeignClient(name = "order-service")
@Component
public interface OrderClient {

    @GetMapping("order/create")
    Boolean create(@RequestParam("userId") long userId ,@RequestParam("productId") long productId);

    @GetMapping("order/prepareCreateOrder")
    Boolean prepareCreateOrder(@RequestParam("orderId")Long orderId,@RequestParam("userId") Long userId,
                          @RequestParam("productId")Long productId,@RequestParam("count") Integer count,@RequestParam("money") BigDecimal money);

}
