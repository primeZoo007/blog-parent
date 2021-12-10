package com.zyg.blog.service;

import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.params.pageParams;

public interface ArticleService {
    Result listArticle(pageParams pageParams);
    /**
     * 分页列表
     * @param pageParams
     * @return
     */
}
