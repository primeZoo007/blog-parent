package com.zyg.blog.vo.params;

import lombok.Data;

@Data
public class pageParams {
    private int page = 1;
    private int pageSize = 10;
}
