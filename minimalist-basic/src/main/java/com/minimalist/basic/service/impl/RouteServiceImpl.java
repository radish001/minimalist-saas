package com.minimalist.basic.service.impl;

import cn.hutool.core.lang.Assert;
import com.minimalist.basic.config.exception.BusinessException;
import com.minimalist.basic.entity.enums.RespEnum;
import com.minimalist.basic.entity.po.MRouteInfo;
import com.minimalist.basic.entity.po.MRoutes;
import com.minimalist.basic.entity.vo.wps.RouteVO;
import com.minimalist.basic.entity.vo.wps.RouteInfoVO;
import com.minimalist.basic.mapper.MRouteInfoMapper;
import com.minimalist.basic.mapper.MRouteMapper;
import com.minimalist.basic.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName RouteServiceImpl.java
 * @Description
 * @createTime 2025-07-10
 * Copyright (C) 2025 HOSE
 */
@Service
public class RouteServiceImpl implements RouteService {


    @Autowired
    private MRouteMapper routeMapper;

    @Autowired
    private MRouteInfoMapper routeInfoMapper;


    @Override
    public List<RouteVO> getRoutes(String tenantId) {
        Assert.notBlank(tenantId, () -> new BusinessException(RespEnum.PARAM_ERROR.getDesc()));
        List<MRoutes> routes = routeMapper.selectByTenantId(Long.getLong(tenantId));
        return convertToVo(routes);
    }

    @Override
    public RouteInfoVO getRouteInfo(String routeId) {
        Assert.notBlank(routeId, () -> new BusinessException(RespEnum.PARAM_ERROR.getDesc()));
        MRouteInfo mRouteInfo = routeInfoMapper.selectByRouteId(Long.getLong(routeId));
        return convertToVo(mRouteInfo);
    }


    private List<RouteVO> convertToVo(List<MRoutes> routes) {
        List<RouteVO> list = new ArrayList<>();
        for(MRoutes mRoutes : routes){
            RouteVO vo = new RouteVO();
            vo.setRouteId(mRoutes.getId());
            vo.setTitle(mRoutes.getTitle());
            list.add(vo);
        }
        return list;
    }


    private RouteInfoVO convertToVo(MRouteInfo mRouteInfo) {
        if(mRouteInfo == null){
            return null;
        }
        RouteInfoVO vo = new RouteInfoVO();
        vo.setRouteId(mRouteInfo.getRouteId());
        vo.setRoadmap(mRouteInfo.getRoadmap());
        vo.setPatternBrainMap(mRouteInfo.getPatternBrainMap());
        vo.setProcessingEvaluation(mRouteInfo.getProcessingEvaluation());
        vo.setAssessmentResult(mRouteInfo.getAssessmentResult());
        return vo;
    }



}
