package com.zyg.blog.controller;

import com.zyg.blog.service.TagService;
import com.zyg.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RestController 返回json数据
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;
    @GetMapping("hot")
    public Result hot(){
        int limit = 6;
        return tagService.hot(limit);
    }
}
