package com.zyc.beancopy.mapper;

import com.zyc.beancopy.po.NestedObject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.print.attribute.standard.Destination;

/**
 * @author zyc66
 */
@Mapper
public interface NestedObjectMapper {


    NestedObjectMapper INSTANCE = Mappers.getMapper(NestedObjectMapper.class);

    NestedObject map(NestedObject source);

}
