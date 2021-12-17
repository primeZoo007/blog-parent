package com.zyg.blog.service.impl;

import com.zyg.blog.dao.mapper.CategoryMapper;
import com.zyg.blog.dao.pojo.Category;
import com.zyg.blog.service.CategoryService;
import com.zyg.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long id) {
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
