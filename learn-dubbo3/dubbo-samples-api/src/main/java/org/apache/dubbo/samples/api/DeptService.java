package org.apache.dubbo.samples.api;

import org.apache.dubbo.samples.po.Dept;
import org.apache.dubbo.samples.po.User;

/**
 * @author zyc66
 */
public interface DeptService {

    Dept getByDeptId(Integer deptId);

}
