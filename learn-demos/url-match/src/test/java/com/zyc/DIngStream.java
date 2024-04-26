package com.zyc;

import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.open.app.api.GenericEventListener;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.dingtalk.open.app.stream.protocol.event.EventAckStatus;

/**
 * @Description
 * @Author zilu
 * @Date 2024/3/7 15:00
 * @Version 1.0.0
 **/
public class DIngStream {

    public static void main(String[] args) throws Exception {
        OpenDingTalkStreamClientBuilder
                .custom()
                .credential(new AuthClientCredential("ding6fdhxdwb0ufs3cgy", "uEaZHPNK9XpXIYMRQR7b4x7-O-KXkfcETw4ltrG62ZZNEAR3EHUJJN-fGGTrMaqf"))
                //注册事件监听
                .registerAllEventListener(new GenericEventListener() {
                    public EventAckStatus onEvent(GenericOpenDingTalkEvent event) {
                        try {
                            //事件唯一Id
                            String eventId = event.getEventId();
                            //事件类型
                            String eventType = event.getEventType();
                            //事件产生时间
                            Long bornTime = event.getEventBornTime();
                            //获取事件体
                            JSONObject bizData = event.getData();
                            //处理事件
                            process(bizData);
                            //消费成功
                            return EventAckStatus.SUCCESS;
                        } catch (Exception e) {
                            //消费失败
                            return EventAckStatus.LATER;
                        }
                    }
                })
                .build().start();
    }

    private static void process(JSONObject bizData) {

        System.out.println(bizData);
    }


}
