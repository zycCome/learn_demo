package com.zyc.boot.controller;


import com.zyc.boot.service.DevelopTaskService;
import com.zyc.boot.service.impl.DevelopTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 开发任务和项目关联关系表 前端控制器
 * </p>
 *
 * @author hejx
 * @since 2021-09-30
 */
@RestController
@RequestMapping("/developTaskProject")
public class DevelopTaskProjectController {

    @Autowired
    private DevelopTaskService developTaskService;

    /**
     * 测试嵌套事务-外部抛异常
     *
     * @param throwE
     * @return
     */
    @GetMapping("/2table")
    public String testTransaction(@RequestParam("throwE") boolean throwE) {
        developTaskService.insert2Table(throwE);
        return "2table";
    }

    /**
     * 测试嵌套事务-内部抛异常
     *
     * @param throwE
     * @return
     */
    @GetMapping("/2table1")
    public String testTransaction1(@RequestParam("throwE") boolean throwE) {
        developTaskService.insert2Table1(throwE);
        return "2table1";
    }

    /**
     * 测试嵌套事务-内部抛异常（内部事务-传播级别-NESTED）
     *
     * @param throwE
     * @return
     */
    @GetMapping("/2table2")
    public String testTransaction2(@RequestParam("throwE") boolean throwE) {
        developTaskService.insert2Table2(throwE);
        return "2table2";
    }


    /**
     * 测试非public方法事务是否生效.结论：不生效！
     *
     * @return
     */
    @GetMapping("/testNotPublicTransaction")
    public String testNotPublicTransaction() {
        developTaskService.notPublicTransaction();
        return "testPublicTransaction";
    }

}
