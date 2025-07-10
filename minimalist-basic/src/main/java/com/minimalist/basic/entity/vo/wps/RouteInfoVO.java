package com.minimalist.basic.entity.vo.wps;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.minimalist.basic.utils.Add;
import com.minimalist.basic.utils.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName RouteVO.java
 * @Description
 * @createTime 2025-07-09
 * Copyright (C) 2025 HOSE
 */
@Data
@Schema(name = "路线信息详情实体")
public class RouteInfoVO implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "路线信息id不能为空", groups = {Update.class})
    @Schema(name = "routeId", description = "路线信息id", type = "string")
    private Long routeId;

    @NotBlank(message = "路线图不能为空", groups = {Add.class, Update.class})
    @Schema(name = "roadmap", description = "路线图", type = "string")
    private String roadmap;


    @NotBlank(message = "模式脑图不能为空", groups = {Add.class, Update.class})
    @Schema(name = "patternBrainMap", description = "模式脑图", type = "string")
    private String patternBrainMap;


    @NotBlank(message = "处理过程评估信息", groups = {Add.class, Update.class})
    @Schema(name = "processingEvaluation", description = "处理过程评估信息", type = "string")
    private ProcessingEvaluation processingEvaluation;


    @NotBlank(message = "综合评估结果", groups = {Add.class, Update.class})
    @Schema(name = "processingEvaluation", description = "综合评估结果", type = "string")
    private AssessmentResult assessmentResult;




}
