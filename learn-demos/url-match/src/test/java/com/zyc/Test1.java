package com.zyc;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import lombok.Data;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * 测试url匹配的可行性
 *
 * @author zhuyc
 * @date 2021/11/17 16:06
 */

public class Test1 {


    /**
     * 待匹配的url
     */
    private List<String> apis = CollUtil.newArrayList(
            "GET /api/bus/bic/basic/user/list",
            "GET /api/bus/bic/basic/unit/dict",
            "* /api/bus/bic/basic/**",
            "GET /api/bus/bic/basic/user/list",
            "POST /api/bus/bic/basic/user",
            "POST /api/bus/bic/basic/user",
            "DELETE /api/bus/bic/basic/user/{id}"
    );

    private Multimap<String, String> roleData = LinkedListMultimap.create();

    {
        //模拟角色和API的映射关系
        roleData.put("ROLE_A", "GET /api/bus/bic/basic/user/list");
        roleData.put("ROLE_A", "GET /api/bus/bic/basic/unit/dict");
        roleData.put("ROLE_A", "* /api/bus/bic/basic/**");
        roleData.put("ROLE_A", "POST /api/bus/bic/basic1/user");
        roleData.put("ROLE_B", "POST /api/bus/bic/basic/user");
        roleData.put("ROLE_C", "DELETE /api/bus/bic/basic/user/{id}");
        roleData.put("ROLE_C", "POST /api/bus/bic/basic2/user");
        roleData.put("ROLE_C", "* /api/bus/bic/basic/**");
        roleData.put("ROLE_D", "DELETE /api/bus/bic/basic/user/{id}");
    }

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    //    /**
//     * 应用级别的权限缓存，除非应用更新注册的信息，否则这份缓存不会变
//     */
//    /**
//     * Method=DELETE 的API集合
//     */
//    List<RequestInfo> deleteApis = new LinkedList<>();
//    List<RequestInfo> getApis = new LinkedList<>();
//    List<RequestInfo> postApis = new LinkedList<>();
//    /**
//     * Method=* 的API集合
//     */
//    List<RequestInfo> anyApis = new LinkedList<>();
    Map<String, RequestInfo> apiMap = new HashMap<>();

    /**
     * 角色映射API的缓存，一个API可能会被授权给多个角色。期望角色变动时，缓存只是局部调整
     */
    private Map<String, Set<String>> roleApiMap = new HashMap<>();


    /**
     * 这种方式是根据
     * url -> 遍历匹配所有的权限规则 -> 需要的角色 -> 和当前用户的角色做匹配。
     * 能够支持类似 spring security中 xx.anyRequest().xxx() 这样的用法（似乎没啥用😂）
     * <p>
     * 另一种方式：
     * 当前用户的角色集合 -> 权限规则集合 -> 优先hash匹配 -> 失败则遍历匹配url（更好理解）
     */
    @Test
    public void test() {
        //1 初始化待匹配的API集合.api字符串在注册阶段就校验过了，因此下面不再校验
        for (String api : apis) {
            if (apiMap.containsKey(api)) {
                continue; //避免重复
            }
            String[] split = api.split(" ");
            RequestInfo requestInfo = new RequestInfo(api, split[0], split[1]);
            apiMap.put(api, requestInfo);
        }

        //2 初始化角色和API的映射关系
        for (Map.Entry<String, String> entry : roleData.entries()) {
            String role = entry.getKey();
            Collection<String> values = roleData.get(role);
            for (String value : values) {
                //一个requestInfo可能对应多个Role
                RequestInfo requestInfo = apiMap.get(value);
                if (requestInfo != null) {
                    Set<String> apiSet = roleApiMap.computeIfAbsent(role, k -> new HashSet<>());
                    apiSet.add(requestInfo.getApi());
                }
            }
        }

        // 解析request得到如下信息
        String method = "DELETE";
        String url = "/api/bus/bic/basic/user/list";
        String api = method + " " + url;
        // 获取当前用户拥有的角色列表
        List<String> roles = CollUtil.newArrayList("ROLE_R", "ROLE_C");

        boolean authorization = false;
        //TODO 如果用字典树会不会更好？
        // 用户所有的角色拥有的权限进行hash匹配
        for (String role : roles) {
            Set<String> apiSet = roleApiMap.get(role);
            if (apiSet != null && apiSet.contains(api)) {
                authorization = true;
                break;
            }
        }

        if (authorization) {
            System.out.println("成功");
        } else {
            // 遍历匹配 所有的角色授权的API路径
            for (String role : roles) {
                Set<String> apiSet = roleApiMap.get(role);
                if (apiSet != null && apiSet.parallelStream()
                        .anyMatch(k -> {
                            RequestInfo e = apiMap.get(k);
                            if (e != null) {
                                return antPathMatcher.match(e.path, url)
                                        && (e.getMethod().equals("*") || e.getMethod().equals(method));
                            }
                            return false;

                        })
                ) {
                    System.out.println("成功");
                    return;
                }
            }
            System.out.println("失败");
        }
    }


    @Data
    static class RequestInfo {
        private String method;
        private String path;
        private String api;
//        private Set<String> roles;

        public RequestInfo(String api, String method, String path) {
            this.api = api;
            this.method = method;
            this.path = path;
//            roles = new HashSet<>();
        }
    }

}
