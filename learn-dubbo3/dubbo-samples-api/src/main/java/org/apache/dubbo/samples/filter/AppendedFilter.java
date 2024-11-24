package org.apache.dubbo.samples.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author zyc66
 * @date 2024/11/22 12:11
 **/
@Activate(group= CommonConstants.CONSUMER,order = -10000)
public class AppendedFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result= invoker.invoke(invocation);
        // Obtain the returned value
        Result appResponse = ((AsyncRpcResult) result).getAppResponse();
        System.out.println("filter"+appResponse);
        // Appended value
//        appResponse.setValue(appResponse.getValue()+"'s customized AppendedFilter");
        return result;
    }
}