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
@Schema(name = "雷达图数据实体")
public class RadarMapDataInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @NotNull(message = "维度", groups = {Add.class, Update.class})
    @Schema(name = "item", description = "维度", type = "string")
    private String item;


    @NotNull(message = "数据类型", groups = {Add.class, Update.class})
    @Schema(name = "type", description = "数据类型", type = "string")
    private String type;


    @NotBlank(message = "数据值", groups = {Add.class, Update.class})
    @Schema(name = "score", description = "数据值", type = "string")
    private BigDecimal score;

    
}
