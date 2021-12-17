package com.zyg.blog.controller;

import com.zyg.blog.service.ArticleService;
import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.params.pageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// JSON数据进行交互
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result ListArticle(@RequestBody pageParams pageParams){
         return articleService.listArticle(pageParams);
    }
    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }
    @PostMapping("new")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }
    @PostMapping("listArchives")
    public Result listArchives(){
        int limit = 5;
        return articleService.listArchives(limit);
    }
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }
}
