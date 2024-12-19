package com.example.wmsbackend.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wmsbackend.entity.ResponsePage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PaginationUtil {

    // 通用的分页数据处理方法
    public static <T, V> ResponsePage<V> getPaginatedData(Page<T> page,
                                                          Function<Page<T>, Page<T>> queryFunction,
                                                          Function<T, V> converter) {
        // 执行查询获取分页结果
        Page<T> queryPage = queryFunction.apply(page);
        ResponsePage<V> responsePage = new ResponsePage<>();

        // 将查询到的实体数据转换为对应的VO对象
        List<T> records = queryPage.getRecords();
        List<V> voList = new ArrayList<>();
        for (T entity : records) {
            voList.add(converter.apply(entity));
        }

        // 设置总数和转换后的记录
        responsePage.setTotal(queryPage.getTotal());
        responsePage.setRecords(voList);

        return responsePage;
    }
}

