package com.zyg.blog.service;

import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hot(int limit);

}
