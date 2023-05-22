package com.zyc.beancopy.mapper;

import com.zyc.beancopy.entity.UserEntity1;
import com.zyc.beancopy.po.UserPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


/**
 * 定义mapstruct接口，在接口上打上@Mapper注解。
 *
 * 接口中有一个常量和一个方法，常量的值是接口的实现类，这个实现类是Mapstruct默认帮我们实现的，下文会讲到。
 * 定义了一个po2entity的转换方法，表示把入参UserPo对象，转换成UserEntity。
 *
 * 注意@Mapper是Mapstruct的注解，不要引错了。
 */
@Mapper
public interface IPersonMapper1 {
    IPersonMapper1 INSTANCT = Mappers.getMapper(IPersonMapper1.class);


    @Mapping(target = "userNick1", source = "userNick")
    UserEntity1 po2entity(UserPo userPo);
}
