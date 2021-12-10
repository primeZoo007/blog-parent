package com.zyg.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyg.blog.dao.mapper.ArticleMapper;
import com.zyg.blog.dao.pojo.Article;
import com.zyg.blog.service.ArticleService;
import com.zyg.blog.service.SysUserService;
import com.zyg.blog.service.TagService;
import com.zyg.blog.vo.ArticleVo;
import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.params.pageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
// 注入到spring容器中
@Service
public class ArticleServiceImpl implements ArticleService  {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public Result listArticle(pageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate,Article::getWeight);
        Page<Article> articlePage = articleMapper.selectPage(page,queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> ArticleVoList = copyList(records,true,true);
        return Result.success(ArticleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records ,boolean isTag,boolean isAuthor) {
        List<ArticleVo> ArticleVoList = new ArrayList<>();
        for (Article record : records) {
            ArticleVoList.add(copy(record,isTag,isAuthor));
        }
        return ArticleVoList;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor){
        ArticleVo ArticleVo = new ArticleVo();
        BeanUtils.copyProperties(article,ArticleVo);
        // 此处数据类型不一致
        ArticleVo.setCreateDate(new DateTime(ArticleVo.getCreateDate()).toString("yyyy-MM-dd"));
        if(isTag){
            Long articleId = article.getId();
            ArticleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId = article.getAuthorId();
            System.out.println("authorId--"+authorId);
            ArticleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        return ArticleVo;
    }
}
