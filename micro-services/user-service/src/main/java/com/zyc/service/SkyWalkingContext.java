package com.zyc.service;

/**
 * @author zyc66
 * @date 2024/11/25 14:58
 **/

import java.util.Base64;
import java.util.UUID;

public class SkyWalkingContext {
    private String traceId;
    private String parentSpanId;
    private boolean sampled;
    private byte[] contextData;

    public SkyWalkingContext() {
        this.traceId = UUID.randomUUID().toString().replaceAll("-", "");
        this.parentSpanId = "0"; // 假设是根跨度
        this.sampled = true; // 强制采样
        this.contextData = new byte[0]; // 没有额外的上下文数据
    }

    // Getter 和 Setter 方法
    // ...

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public boolean isSampled() {
        return sampled;
    }

    public void setSampled(boolean sampled) {
        this.sampled = sampled;
    }

    public byte[] getContextData() {
        return contextData;
    }

    public void setContextData(byte[] contextData) {
        this.contextData = contextData;
    }

    public String generateSw8HeaderValue() {
        // 采样标志，1 表示采样，0 表示不采样
        String sampledFlag = sampled ? "1" : "0";
        // 没有额外的上下文数据，所以使用 Base64 编码一个空的 byte 数组
        String encodedContextData = Base64.getEncoder().encodeToString(contextData);
        // sw8 头部信息格式：traceId-parentSpanId-sampled-flag-encodedContextData
        return String.format("%s-%s-%s-0-%s", traceId, parentSpanId, sampledFlag, encodedContextData);
    }


    public static void main(String[] args) {
        SkyWalkingContext context = new SkyWalkingContext();
        String sw8HeaderValue = context.generateSw8HeaderValue();
        System.out.println("Generated SW8 header value: " + sw8HeaderValue);
    }
}


