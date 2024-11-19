package org.apache.dubbo.samples.api;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.samples.po.User;

/**
 * @author zyc66
 */
public interface UserService {

    User getByUserId(Integer userId);

    User getByTag(String tag);


    public String testDynamicConfig(long sleepTime) ;

    public StreamObserver<String> sayHelloStream(StreamObserver<String> response) ;

}
