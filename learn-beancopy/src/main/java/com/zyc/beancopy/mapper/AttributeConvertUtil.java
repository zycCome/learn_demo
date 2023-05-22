package com.zyc.beancopy.mapper;

import cn.hutool.json.JSONUtil;
import com.zyc.beancopy.entity.Attributes;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/18 11:18 AM
 * @Version 1.0.0
 **/
public class AttributeConvertUtil {

    /**
     * json字符串转对象
     *
     * @param jsonStr
     * @return
     */
    @Named("jsonToObject")
    public Attributes jsonToObject(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        return JSONUtil.toBean(jsonStr, Attributes.class);
    }

}
