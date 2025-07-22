package com.minimalist.basic.service.impl;

import cn.hutool.core.lang.Assert;
import com.minimalist.basic.config.exception.BusinessException;
import com.minimalist.basic.entity.enums.RespEnum;
import com.minimalist.basic.entity.po.MRouteInfo;
import com.minimalist.basic.entity.po.MRoutes;
import com.minimalist.basic.entity.vo.wps.RouteVO;
import com.minimalist.basic.entity.vo.wps.RouteInfoVO;
import com.minimalist.basic.mapper.MWpsRouteInfoMapper;
import com.minimalist.basic.mapper.MWpsRoutesMapper;
import com.minimalist.basic.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private MWpsRoutesMapper routeMapper;

    @Autowired
    private MWpsRouteInfoMapper routeInfoMapper;


    @Override
    public List<RouteVO> getRoutes(String tenantId) {
        Assert.notBlank(tenantId, () -> new BusinessException(RespEnum.PARAM_ERROR.getDesc()));
        List<MRoutes> routes = routeMapper.selectByTenantId(Long.parseLong(tenantId));
        List<Long> routeIds = routes.stream().map(MRoutes::getId).toList();
        List<MRouteInfo> routeInfos = routeInfoMapper.selectRouteInfoByRouteIds(routeIds);
        Map<Long, MRouteInfo> routeInfoMap = routeInfos.stream().collect(Collectors.toMap(MRouteInfo::getRouteId, Function.identity()));
        return convertToVo(routes, routeInfoMap);
    }

    @Override
    public RouteInfoVO getRouteInfo(String routeId) {
        Assert.notBlank(routeId, () -> new BusinessException(RespEnum.PARAM_ERROR.getDesc()));
        MRouteInfo mRouteInfo = routeInfoMapper.selectRouteInfoByRouteId(Long.parseLong(routeId));
        return convertToVo(mRouteInfo);
    }


    private List<RouteVO> convertToVo(List<MRoutes> routes, Map<Long, MRouteInfo> mRouteInfoMap) {
        List<RouteVO> list = new ArrayList<>();
        for(MRoutes mRoutes : routes){
            RouteVO vo = new RouteVO();
            vo.setRouteId(mRoutes.getId());
            vo.setTitle(mRoutes.getTitle());
            vo.setContent(convertToVo(mRouteInfoMap.get(mRoutes.getId())));
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
