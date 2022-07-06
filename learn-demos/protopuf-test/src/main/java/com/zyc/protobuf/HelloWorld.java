//package com.zyc.protobuf;
//
///**
// * @author zhuyc
// * @date 2022/06/12 14:54
// **/
//public class HelloWorld {
//
//    public static void main(String[] args) {
//        ResponseOuterClass.Response.Builder builder = ResponseOuterClass.Response.newBuilder();
//        // 设置字段值
//        builder.setData("hello www.tizi365.com");
//        builder.setStatus(200);
//
//        ResponseOuterClass.Response response = builder.build();
//        // 将数据根据protobuf格式，转化为字节数组
//        byte[] byteArray = response.toByteArray();
//
//        // 反序列化,二进制数据
//        try {
//            ResponseOuterClass.Response newResponse = ResponseOuterClass.Response.parseFrom(byteArray);
//            System.out.println(newResponse.getData());
//            System.out.println(newResponse.getStatus());
//        } catch (Exception e) {
//        }
//
//    }
//
//}
