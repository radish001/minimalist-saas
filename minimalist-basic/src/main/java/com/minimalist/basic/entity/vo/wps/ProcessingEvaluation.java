package com.minimalist.basic.entity.vo.wps;

import com.fasterxml.jackson.databind.JsonSerializer;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName ProcessingEvaluation.java
 * @Description TODO
 * @createTime 2025-07-09
 * Copyright (C) 2025 HOSE
 */
@Data
@Schema(name = "处理过程评估信息")
public class ProcessingEvaluation implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;


    @NotNull(message = "运收数据", groups = {Add.class, Update.class})
    @Schema(name = "operatingRevenue", description = "运收数据", type = "array")
    private List<DataInfo> operatingRevenue;



    @NotNull(message = "处理数据", groups = {Add.class, Update.class})
    @Schema(name = "dispose", description = "处理数据", type = "array")
    private GroupHistogramDataInfo dispose;


    @NotBlank(message = "处置数据", groups = {Add.class, Update.class})
    @Schema(name = "disposition", description = "处置数据", type = "string")
    private GroupHistogramDataInfo disposition;




}
