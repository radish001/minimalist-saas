package com.minimalist.basic.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author huxiaodong
 * @version 1.0.0
 * @ClassName WpsEnum.java
 * @Description TODO
 * @createTime 2025-07-10
 * Copyright (C) 2025 HOSE
 */
public class WpsEnum {
    /** 文件处理异常信息 */
    @Getter
    @AllArgsConstructor
    public enum ErrorMsg {
        FILE_DELETE_FAIL("文件删除失败，请重试"),
        ;
        private final String desc;
    }


}
