package com.example.wmsbackend.converter;

import cn.hutool.core.date.DateUtil;
import com.example.wmsbackend.entity.User;
import com.example.wmsbackend.entity.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.util.Date;

@Mapper
public interface UserConverterMapper {
    UserConverterMapper INSTANCE = Mappers.getMapper(UserConverterMapper.class);

    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "timestampToDate")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "timestampToDate")
    UserVo toVO(User user);

    @Mapping(source = "status", target = "status", qualifiedByName = "stringToStatus")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "dateToTimestamp")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "dateToTimestamp")
    @Mapping(source = "password", target = "password")
    User toPO(UserVo userVO);

    @Named("statusToString")
    static String statusToString(Integer status) {
        return status == 0 ? "启用" : "禁用";
    }

    @Named("stringToStatus")
    static Integer stringToStatus(String status) {
        return "启用".equals(status) ? 0 : 1;
    }

    @Named("timestampToDate")
    static String timestampToDate(Timestamp timestamp) {
        return timestamp == null ? null : DateUtil.format(new Date(timestamp.getTime()), "yyyy-MM-dd HH:mm:ss");
    }

    @Named("dateToTimestamp")
    static Timestamp dateToTimestamp(String date) {
        return date == null ? null : Timestamp.valueOf(date);
    }
}

