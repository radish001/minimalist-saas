package com.minimalist.basic.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.minimalist.basic.entity.vo.wps.RouteVO;
import com.minimalist.basic.entity.vo.wps.RouteInfoVO;
import com.minimalist.basic.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/basic/wps/technical")
@Tag(name = "技术路线管理")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/routes")
    @SaCheckPermission("basic:wps:routes")
    @Operation(summary = "查询技术路线图")
    public ResponseEntity<List<RouteVO>> getRoutes(@RequestParam String tenantId) {
        return ResponseEntity.ok(routeService.getRoutes(tenantId));
    }


    @GetMapping("/route")
    @SaCheckPermission("basic:wps:routeInfo")
    @Operation(summary = "查询技术路线图详情")
    public ResponseEntity<RouteInfoVO> getRouteInfo(@RequestParam("routeId") String routeId) {
        return ResponseEntity.ok(routeService.getRouteInfo(routeId));
    }

}
