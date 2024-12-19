package com.example.wmsbackend.converter;

import cn.hutool.core.date.DateUtil;
import com.example.wmsbackend.entity.Warehouse;
import com.example.wmsbackend.entity.vo.WarehouseVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.util.Date;

@Mapper
public interface WarehouseConverterMapper {
    WarehouseConverterMapper INSTANCE = Mappers.getMapper(WarehouseConverterMapper.class);

    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "timestampToDate")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "timestampToDate")
    WarehouseVo toVO(Warehouse warehouse);

    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "dateToTimestamp")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "dateToTimestamp")
    Warehouse toPO(WarehouseVo warehouseVO);

    @Named("timestampToDate")
    static String timestampToDate(Timestamp timestamp) {
        return timestamp == null ? null : DateUtil.format(new Date(timestamp.getTime()), "yyyy-MM-dd HH:mm:ss");
    }

    @Named("dateToTimestamp")
    static Timestamp dateToTimestamp(String date) {
        return date == null ? null : Timestamp.valueOf(date);
    }
}


