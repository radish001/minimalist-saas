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
import java.math.BigDecimal;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName DataInfo.java
 * @Description TODO
 * @createTime 2025-07-09
 * Copyright (C) 2025 HOSE
 */
@Data
@Schema(name = "直方图数据实体")
public class HistogramDataInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @NotNull(message = "数据名称", groups = {Add.class, Update.class})
    @Schema(name = "name", description = "数据名称", type = "string")
    private String name;



    @NotNull(message = "直方图名称", groups = {Add.class, Update.class})
    @Schema(name = "group", description = "直方图名称", type = "string")
    private String group;


    @NotBlank(message = "数据值", groups = {Add.class, Update.class})
    @Schema(name = "value", description = "数据值", type = "string")
    private BigDecimal value;

    
}
