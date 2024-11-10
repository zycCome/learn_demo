package com.zyc.mybatis;

import com.zyc.mybatis.mapper.UserMapper;
import com.zyc.mybatis.mapper.UserRandomKeyMapper;
import com.zyc.mybatis.mapper.UserRandomKeyMapper2;
import com.zyc.mybatis.pojo.User;
import com.zyc.mybatis.pojo.UserRandomKey;
import com.zyc.mybatis.pojo.UserRandomKey2;
import com.zyc.mybatis.service.UserService;
import com.zyc.mybatis.util.SnowflakeIdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author zhuyc
 * @date 2022/04/21 12:43
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private UserService userService;

    private SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0);

    @Autowired
    private UserRandomKeyMapper userRandomKeyMapper;

    @Autowired
    private UserRandomKeyMapper2 userRandomKeyMapper2;

    @Test
    public void contextLoads() {
        /**
         * spring整合mybatis后，sqlsession的创建时机：
         * Mapper里面持有一个SqlSessionTemplate对象，该对象实现了SqlSession接口。
         * 调用对应方法时，会交给内部SqlSessionInterceptor对象，该对象会尝试从Spring的事务管理器中获取当前线程已经持有的SqlSession。如果获取失败，则创建一个新的
         */
        List<User> list = userMapper.list();
        // 查询结果为空也会缓存空集合
        System.out.println(list);
        System.out.println("------------");
        List<User> list2 = userMapper.list();
        System.out.println(list);
        // 肯定不一样，因为已经是两个session了
        System.out.println(list2 == list);
    }

    @Test
    public void cacheInTransactionTest() {
        userService.cacheInTransactionTest();
    }



    // 第一次 1w 49066
    // 第二次 1w 61066
    // 第三次 1w 45817
    // 第四次 1w 46666
    // 第五次 1w 50342
    // 第六次 1w 51230
    // 第七次 1w 57515
    @Test
    public void testIncrIdInsert() throws IOException {
//        List<UserRandomKey> userKeyAutoList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 99; i < 10000 + 99; i++) {
            UserRandomKey userKeyAuto = UserRandomKey.builder()
                    .id(idWorker.getId())
                    .userId((long) i + 1)
                    .userName("xiaoming")
                    .address("金鹰")
                    .city("南京")
                    .email("qq@qq.com")
                    .sex(1)
                    .state(1)
                    .build();
            userRandomKeyMapper.insert(userKeyAuto);

        }

        System.out.println("递增主键插入"+(System.currentTimeMillis()-startTime));
    }


    // 第一次 1w 56907
    // 第二次 1w 42276
    // 第三次 1w 43371
    // 第四次 1w 57974
    // 第五次 1w 53472
    // 第六次 1w 53472
    // 第七次 1w 50386
    @Test
    public void testDecrIdInsert() throws IOException {
//        List<UserRandomKey> userKeyAutoList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 99; i < 10000 + 99; i++) {
            UserRandomKey2 userKeyAuto = UserRandomKey2.builder()
                    .id(idWorker.getDescId())
                    .userId((long) i + 1)
                    .userName("xiaoming")
                    .address("金鹰")
                    .city("南京")
                    .email("qq@qq.com")
                    .sex(1)
                    .state(1)
                    .build();
            userRandomKeyMapper2.insert(userKeyAuto);

        }

        System.out.println("递减主键插入:"+(System.currentTimeMillis()-startTime));
    }


    /**
     * java 东11区，MySQL 东8区，serverTimezone=Asia/Shanghai 也是东8区
     *
     * 插入Date类型和LocalDateTime类型，数据库都是dateTime类型
     *
     * 结果：
     * 实际时间 18：00（东8区）
     * 插入前：date和localDateTime都是 21：00
     * 数据库中：date：18：00   localDateTime：21：00  （因为LocalDateTime没有时区信息，所以没有转换）（所以转换的判断标准是字段类型！！！Date就会转）
     * 查询后：date和localDateTime都是 21：00  （因为LocalDateTime没有时区信息，所以没有转换，Date有时区信息，转换了？）
     *
     * ------
     * 如果serverTimezone不指定，则date和localDateTime在数据库中也是21：00，没有发生时区转换（难道不取的情况下，认为数据库和本地一致？？？）
     * 返回的数据和插入的一致这点放心
     *
     *
     * @throws IOException
     */
    @Test
    public void testTimeZone1() throws IOException {
        TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
        TimeZone.setDefault(timeZone);

        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("date:"+date+"   localDateTime:"+localDateTime);
        UserRandomKey2 userKeyAuto = UserRandomKey2.builder()
                .id(4L)
                .userId(1L)
                .userName("xiaoming")
                .address("金鹰")
                .city("南京")
                .email("qq@qq.com")
                .sex(1)
                .state(1)
                .date(date)
                .localDateTime(localDateTime)
                .build();
        userRandomKeyMapper2.insert(userKeyAuto);

        UserRandomKey2 userRandomKey = userRandomKeyMapper2.selectByPrimaryKey(4L);
        System.out.println(userRandomKey);
    }



}
