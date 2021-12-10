package com.zyg.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyg.blog.dao.pojo.Tag;
import com.zyg.blog.vo.TagVo;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHotsTagId(int limit);

    List<Tag> findTagsByTagId(List<Long> tagIds);
}
