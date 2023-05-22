package com.zyc.beancopy.mapper;

import com.zyc.beancopy.entity.UserEntity2;
import com.zyc.beancopy.po.UserPo2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author jiangzhengyin
 */
@Mapper(uses = AttributeConvertUtil.class)
public interface IPersonMapper2 {
    IPersonMapper2 INSTANCT = Mappers.getMapper(IPersonMapper2.class);


    @Mapping(target = "attributes", source = "attributes", qualifiedByName = "jsonToObject")
    @Mapping(target = "userNick1", source = "userNick")
    @Mapping(target = "createTime", source = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "age", source = "age", numberFormat = "#0.00")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userVerified", defaultValue = "defaultValue-2")
    UserEntity2 po2entity(UserPo2 userPo);
}
