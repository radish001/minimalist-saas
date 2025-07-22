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
 * @ClassName RouteInfoVO.java
 * @Description
 * @createTime 2025-07-09
 * Copyright (C) 2025 HOSE
 */
@Data
@Schema(name = "路线信息列表实体")
public class RouteVO implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "路线信息id不能为空", groups = {Update.class})
    @Schema(name = "routeId", description = "路线信息id", type = "string")
    private Long routeId;

    @NotBlank(message = "路线标题不能为空", groups = {Add.class, Update.class})
    @Schema(name = "title", description = "路线信息标题", type = "string")
    private String title;

    @NotNull(message = "路线详情不能为空", groups = {Add.class, Update.class})
    @Schema(name = "content", description = "路线详情", type = "object")
    private RouteInfoVO content;



}
