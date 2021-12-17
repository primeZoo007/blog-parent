package com.zyg.blog.controller;

import com.zyg.blog.service.CommentService;
import com.zyg.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("article/{id}")
    public Result getArticle(@PathVariable("id") Long id){
        return commentService.commemtsByid(id);
    }
}
