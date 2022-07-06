package com.zyc.webclient.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author zilu
 * @Date 2022/7/6 1:41 PM
 * @Version 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpClientResult {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(int code) {
        this.code = code;
    }
}
