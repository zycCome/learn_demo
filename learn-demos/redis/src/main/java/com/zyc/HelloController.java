package com.zyc;

import cn.hutool.core.collection.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.SocketUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zilu
 * @Date 2023/12/14 14:51
 * @Version 1.0.0
 **/
@RestController
@Slf4j
public class HelloController {

    @Autowired
    MessageSource messageSource;


    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/hello")
    public String hello() {
        return messageSource.getMessage("user.name", null, LocaleContextHolder.getLocale());
    }


    @PostMapping("/postBody")
    @ResponseBody
    public User postBody(@RequestBody User user) {
        System.out.println(user);
        return user;
    }


    @PostMapping("/putKey")
    @ResponseBody
    public String putKey(@RequestBody User user) {
        if (StringUtils.isEmpty(user)) {
            return "key must not empty";
        } else {
            if (redisUtil.set(user.getName(), user, 30000)) {
                return "success";
            }
        }
        return "fail";
    }

    @PostMapping("/getKey")
    @ResponseBody
    public String getKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return "key must not empty";
        } else {
            User user = (User) redisUtil.get(key);
            if(user == null) {
                return "null";
            }
            return user.toString();
        }
    }


    @GetMapping("/pipeline")
    @ResponseBody
    public String pipeline() {
//        List<Object> resultList = redisTemplate.executePipelined(new RedisCallback<Object>() {
//
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                // 1、通过connection打开pipeline
//                connection.openPipeline();
//                // 2、给本次pipeline添加一次性要执行的多条命令
//
//                // 2.1、一个 set key value 的操作
//                byte[] key = "name".getBytes();
//                byte[] value = "qinyi".getBytes();
//                connection.set(key, value);
//
//                // 2.2、执行一个错误的命令
//                connection.lPop("xyzabc".getBytes());
//
//                // 2.3、mset 操作
//                Map<byte[], byte[]> tuple = new HashMap<>();
//                tuple.put("id".getBytes(), "1".getBytes());
//                tuple.put("age".getBytes(), "19".getBytes());
//                connection.mSet(tuple);
//
//                /**
//                 * 1、不能关闭pipeline
//                 * 2、返回值为null
//                 */
//                // 3. 关闭 pipeline
//                // connection.closePipeline();
//                return null;
//            }
//        });
//
//        resultList.forEach(System.out::println);


        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        List<String> redisKey = new ArrayList<>();
        HashMap<String, User> redisData = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            redisData.put("xxx-yyy-zzz:pipLine:" + i, User.builder().userId(i).name(i + "").build());
            redisKey.add("xxx-yyy-zzz:pipLine:" + i);
        }
        redisTemplate.opsForValue().multiSet(redisData);

        List multiGetResult = redisTemplate.opsForValue().multiGet(redisKey);

        List<Object> pipeLineResult = redisTemplate.executePipelined(
                new RedisCallback<User>() {
                    @Override
                    public User doInRedis(RedisConnection connection) throws DataAccessException {
                        for (String key : redisKey) {
                            connection.get(keySerializer.serialize(key));
                        }
                        return null;
                    }
                }
        );
        System.out.println("pipeLineResult => " + pipeLineResult.size());
        System.out.println("multiGetResult => " + multiGetResult.size());
        return "ok";
    }

    int testSize = 100000;


    @GetMapping("/buildKeys")
    @ResponseBody
    public String buildKeys() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("redis构建key测试");
        /**
         * 批量插入 ??
         */
        ArrayList<String> redisKey = new ArrayList<>();
        redisKey.add("xxx-yyy-zzz:pipLine:0");
        HashMap<String, User> redisData = new HashMap<>();
        for (int i = 1; i <= testSize; i++) {
            redisKey.add("xxx-yyy-zzz:pipLine:" + i);
            redisData.put("xxx-yyy-zzz:pipLine:" + i, User.builder().userId(i).build());
        }
        stopWatch.start("构建key");
        redisTemplate.opsForValue().multiSet(redisData);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return "buildKeys";
    }


    @GetMapping("/mGet")
    @ResponseBody
    public String mGet() {
        StopWatch stopWatch = new StopWatch("redis mGet 测试");
        ArrayList<String> redisKey = new ArrayList<>();
        redisKey.add("xxx-yyy-zzz:pipLine:0");
        for (int i = 1; i <= testSize; i++) {
            redisKey.add("xxx-yyy-zzz:pipLine:" + i);
        }
        stopWatch.start("multiGet");
        List<Object> multiGetResult = redisTemplate.opsForValue().multiGet(redisKey);
        stopWatch.stop();

        System.out.println("multiGetResult => " + multiGetResult.size());
        System.out.println(multiGetResult.get(testSize));
        System.out.println(stopWatch.prettyPrint());
        return "mGet";
    }


    /**
     * 管道和mGet效率差不多，本地16-20s左右
     * @return
     */
    @GetMapping("/clusterPipLineGet")
    @ResponseBody
    public String clusterPipLineGet() {
        /**
         * 集群模式下的PipLine
         */
        StopWatch stopWatch = new StopWatch("redis pipeline 测试");

        ArrayList<String> redisKey = new ArrayList<>();
        redisKey.add("xxx-yyy-zzz:pipLine:0");
        for (int i = 1; i <= testSize; i++) {
            redisKey.add("xxx-yyy-zzz:pipLine:" + i);
        }
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        stopWatch.start("clusterPipLineGet");
        List<Object> pipeLineResult = redisTemplate.executePipelined(
                new RedisCallback<User>() {
                    @Override
                    public User doInRedis(RedisConnection connection) throws DataAccessException {
                        for (String key : redisKey) {
                            connection.get(keySerializer.serialize(key));
                        }

                        // 更多命令也都支持
                        RedisZSetCommands.Tuple tuple1 = new DefaultTuple("V1".getBytes(),11.0);
                        HashSet set = new HashSet();
                        set.add(tuple1);
                        connection.zSetCommands().zAdd(keySerializer.serialize("set1"), set);
                        return null;
                    }
                }
        );
        stopWatch.stop();
        System.out.println("clusterPipLineGet => " + pipeLineResult.size());
        System.out.println(pipeLineResult.get(testSize));
        System.out.println(stopWatch.prettyPrint());
        return "clusterPipLineGet";
    }


    /**
     * 单个获取和批量获取差别太大太大了
     * @return
     */
    @GetMapping("/muchGet")
    @ResponseBody
    public String muchGet() {
        StopWatch stopWatch = new StopWatch("redis muchGet 测试");

        ArrayList<String> redisKey = new ArrayList<>();
        redisKey.add("xxx-yyy-zzz:pipLine:0");
        for (int i = 1; i <= testSize; i++) {
            redisKey.add("xxx-yyy-zzz:pipLine:" + i);
        }

        List<Object> pipeLineResult = new ArrayList<>();
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        stopWatch.start("muchGet");
        for (String s : redisKey) {
            Object o = redisTemplate.opsForValue().get(s);
            pipeLineResult.add(o);
        }
        stopWatch.stop();
        System.out.println("muchGet => " + pipeLineResult.size());
        System.out.println(pipeLineResult.get(testSize));
        System.out.println(stopWatch.prettyPrint());
        return "muchGet";
    }


    @GetMapping("/publish")
    public String publish(@RequestParam String message) {

        // 发送消息
        redisTemplate.convertAndSend("channel1", message);
        return "publish";
    }


    private boolean subscribed = false;

    private Subscription subscription;

    /**
     * 集群环境下可以使用发布订阅
     * 测试结果
     * 1. 即使重新选主，之前的订阅也是有效的
     * @return
     */
    @GetMapping("/subscribe")
    public String subscribe() {
        if(!subscribed) {
            subscribed =true;
            RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
            redisConnection.subscribe(new MessageListener() {
                @Override
                public void onMessage(Message message, byte[] bytes) {
                    // 收到消息的处理逻辑
                    log.info("Receive message : " + message);
                }
            }, "channel1".getBytes(StandardCharsets.UTF_8));
            subscription = redisConnection.getSubscription();

            return "subscribe success";
        } else {
            subscribed=false;
            subscription.unsubscribe();
            subscription = null;
        }
        return "subscribed";
    }


}
