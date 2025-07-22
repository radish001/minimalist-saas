package com.minimalist.basic.mapper;

import com.minimalist.basic.entity.po.MRouteInfo;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.List;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName RouteMapper.java
 * @Description
 * @createTime 2025-07-10
 * Copyright (C) 2025 HOSE
 */
public interface MWpsRouteInfoMapper extends BaseMapper<MRouteInfo> {



    default MRouteInfo selectRouteInfoByRouteId(Long routeId) {
        return selectOneByQuery(QueryWrapper.create().eq(MRouteInfo::getRouteId, routeId));
    }


    default List<MRouteInfo> selectRouteInfoByRouteIds(List<Long> routeIds) {
        return selectListByQuery(QueryWrapper.create().in(MRouteInfo::getRouteId, routeIds));
    }
}
