package com.zyc.mybatis;

import com.zyc.mybatis.mapper.UserMapper;
import com.zyc.mybatis.mapper.UserRandomKeyMapper;
import com.zyc.mybatis.mapper.UserRandomKeyMapper2;
import com.zyc.mybatis.pojo.User;
import com.zyc.mybatis.pojo.UserRandomKey;
import com.zyc.mybatis.pojo.UserRandomKey2;
import com.zyc.mybatis.util.SnowflakeIdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author zhuyc
 * @date 2022/04/21 12:43
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisApplicationTests {

    @Autowired
    UserMapper userMapper;

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


}
