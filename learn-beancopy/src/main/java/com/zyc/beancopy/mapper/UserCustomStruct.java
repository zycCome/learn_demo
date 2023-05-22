//package com.zyc.beancopy.mapper;
//
//import com.zyc.beancopy.dto.UserCustomDTO;
//import com.zyc.beancopy.dto.UserExtDTO;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
///**
// * @Description
// * @Author zilu
// * @Date 2023/5/18 11:32 AM
// * @Version 1.0.0
// **/
//@Mapper(componentModel = "spring")
//public interface UserCustomStruct {
//
//    @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyy-MM-dd")
//    @Mapping(target = "regDate", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(userDO.getRegDate(),\"yyyy-MM-dd HH:mm:ss\"))")
//    @Mapping(source = "userExtDO", target = "userExtDTO")
//    @Mapping(target = "memo", ignore = true)
//    UserCustomDTO toUserCustomDTO(UserDO userDO);
//
//
//    /**
//     * 当映射 UserExtDTO 对象的时候，会自动调用该接口中的自定义 toUserExtDTO 方法，完成自定义映射。
//     * @param userExtDO
//     * @return
//     */
//    default UserExtDTO toUserExtDTO(UserExtDO userExtDO) {
//        UserExtDTO userExtDTO = new UserExtDTO();
//        userExtDTO.setKids(userExtDO.getKids());
//        userExtDTO.setFavorite(userExtDO.getFavorite());
//
//        // 覆盖这两个值
//        userExtDTO.setRegSource("默认来源");
//        userExtDTO.setSchool("默认学校");
//
//        return userExtDTO;
//    }
//
//}
