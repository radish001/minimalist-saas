package com.minimalist.basic.entity.po;

import com.minimalist.basic.config.mybatis.InsertFullColumnHandler;
import com.minimalist.basic.config.mybatis.UpdateFullColumnHandler;
import com.minimalist.basic.config.mybatis.bo.BaseEntity;
import com.minimalist.basic.entity.vo.wps.AssessmentResult;
import com.minimalist.basic.entity.vo.wps.ProcessingEvaluation;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.JacksonTypeHandler;
import lombok.*;

import java.io.Serializable;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName MRoute.java
 * @Description TODO
 * @createTime 2025-07-10
 * Copyright (C) 2025 HOSE
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "m_wps_route_info", onInsert = InsertFullColumnHandler.class, onUpdate = UpdateFullColumnHandler.class)
public class MRouteInfo extends BaseEntity implements Serializable {

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 路线id
     */
    private Long routeId;

    /**
     * 路线图
     */
    private String roadmap;

    /**
     * 模式脑图
     */
    private String patternBrainMap;

    /**
     * 处理过程评估信息
     */
    @Column(typeHandler = JacksonTypeHandler.class)
    private ProcessingEvaluation processingEvaluation;

    /**
     * 综合评估结果信息
     */
    @Column(typeHandler = JacksonTypeHandler.class)
    private AssessmentResult assessmentResult;
}
