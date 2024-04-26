package com.zyc.signature;

/**
 * @Description
 * @Author zilu
 * @Date 2024/3/26 18:26
 * @Version 1.0.0
 **/

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;

import java.io.File;

public class Demo2 {

    public static void main(String[] args) throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        //从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
//        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "hz-b1";
        // 填写不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。
        String objectName = "hz/示例卡片.json";
        // 填写Object下载到本地的完整路径。
        String pathName = "/Users/zilu/Desktop/full1.json";

        String accessKeyId = "STS.";
        String accessKeySecret = "";
        String accessKeyId1 = "STS.";
        String accessKeySecret1 = "";
//生成的安全令牌SecurityToken。
        String securityToken = "+DiuxmxbWSZ3zWgkseT8sdgPbElDz2IHhMe3ZhBO4Xt/00nm1V7/cflqxuUJJfHBQY/3TMYc0Fnzm6aq/t5uaXj9Vd+rDHdEGXDxnkprywB8zyUNLafNq0dlnAjVUd6LDmdDKkLTfHWN/z/vwBVNkMWRSiZjdrHcpfIhAYyPUXLnzML/2gQHWI6yjydBM051Un0zkhtPnmnpTAsUXk4QekmrNPlePYOYO5asRgBpB7Xuqu0fZ+Hqi7i3YItEcbqfkn1fUfo2qa54DAGT9d5AqAN+3d/sNuIAJ+fbjv733VET1f/xqAAa5k3Qds9Z2fZgNi4mACuDNkJnUCqpopblrQD+XKB+oMbeTCE4ZvaB6Qt3keOLzfMxgI1aZ1El8ERbSj/Z6mf33XQ8IWtTt4eVdIS99C9mnEW2+FR0ugtWesMeDJv6tVjbi+n8G94L6ivRGVhe39lCZG6vacfGNh8OxpE5dvaIt9IAA=";
        String securityToken1 = "+vGCW3zhsnEQe8hD15zK1Dz2IHhMe3ZhBO4Xt/00nm1V7/cflqxuUJJfHBQwwFjMYc0Fnzm6aq/t5uauj9Vd+pvHdEGXDxnkpoCwB8zyUNLafNq0dlnAjVUd6LDmdDKkLTvHVJqSksxoY8gwVAu1ZiY8ULUwHAZ5r9IAPnb8LOukNgWQ4lDdF011oAFx+ysdn6202Z+b8QGMzg+4mO8H4o6oZZi2KJA1Zc4mFdWt084OL/acj3UKsUYXqf4q1fAfpg2q5I/NXwNjhDydKPfR/9FVKwt0W7M3AaYsroKnzaUn5b2OydWukk0XYLwMAnnFIoyn383AH+qlKNc1eLHgK6E5AUEy8zqfGoABofXy8cuzKMqC3tUt9RAqDhaxXXFvtscafA5mFNdwxuTk1C8Oa9hY6OhZkCkn1Ss6oTDAa1ngDPyxzPU0ToyWiupq1NNJzhKcJLhlLRSYCz6WagNw+Bb2Of+a3IW2u0zWrjkqWwmGpAn8QAJ3mLf29lmQDrZo4SefI0dEUmlA5mAgAA==";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret,securityToken);
        try {
            // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
            // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(pathName));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}