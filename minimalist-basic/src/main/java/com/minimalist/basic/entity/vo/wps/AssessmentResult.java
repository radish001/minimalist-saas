package com.minimalist.basic.entity.vo.wps;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.minimalist.basic.utils.Add;
import com.minimalist.basic.utils.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName AssessmentResult.java
 * @Description TODO
 * @createTime 2025-07-09
 * Copyright (C) 2025 HOSE
 */
@Data
@Schema(name = "综合评估结果信息")
public class AssessmentResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @NotNull(message = "数据名称", groups = {Add.class, Update.class})
    @Schema(name = "dataNames", description = "数据名称", type = "array")
    private List<String> dataNames;



    @NotNull(message = "雷达图数据", groups = {Add.class, Update.class})
    @Schema(name = "data", description = "雷达图数据", type = "array")
    private List<RadarMapDataInfo> data;
}
