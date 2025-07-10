package com.minimalist.basic.service;

import com.minimalist.basic.entity.vo.wps.RouteVO;
import com.minimalist.basic.entity.vo.wps.RouteInfoVO;

import java.util.List;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName RouteService.java
 * @Description
 * @createTime 2025-07-09
 * Copyright (C) 2025 HOSE
 */
public interface RouteService {


    /**
     * 获取路线图列表
     * @param tenantId
     */
    List<RouteVO> getRoutes(String tenantId);


    /**
     * 获取路线详情
     * @param routeId
     */
    RouteInfoVO getRouteInfo(String routeId);
}
