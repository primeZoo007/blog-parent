package com.zyg.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyg.blog.dao.dos.Achives;
import com.zyg.blog.dao.mapper.ArticleMapper;
import com.zyg.blog.dao.mapper.ArticleBodyMapper;
import com.zyg.blog.dao.pojo.Article;
import com.zyg.blog.dao.pojo.ArticleBody;
import com.zyg.blog.service.ArticleService;
import com.zyg.blog.service.CategoryService;
import com.zyg.blog.service.SysUserService;
import com.zyg.blog.service.TagService;
import com.zyg.blog.vo.ArticleBodyVo;
import com.zyg.blog.vo.ArticleVo;
import com.zyg.blog.vo.CategoryVo;
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
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CategoryService categoryService;
    @Override
    public Result hotArticle(int limit){
        LambdaQueryWrapper<Article> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        // select id,title from Article order by view_count desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false,false,false));

    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        // select id,title from Article order by view_count desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false,false,false));
    }

    @Override
    public Result listArchives(int limit) {
        List<Achives> AchivesList = articleMapper.listArchives();
        return Result.success(AchivesList);
    }

    @Override
    public Result findArticleById(Long articleId) {
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article,true,true,true,true);
        // 更新 增加了此次接口的耗时，如果更新出问题，不能影响查看文章的操作
        // 线程池 可以把更新操作扔到线程池中去执行，和主线程就不相关了

        return Result.success(articleVo);
    }

    @Override
    public Result listArticle(pageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate,Article::getWeight);
        Page<Article> articlePage = articleMapper.selectPage(page,queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> ArticleVoList = copyList(records,true,true,false,false);
        return Result.success(ArticleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records ,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> ArticleVoList = new ArrayList<>();
        for (Article record : records) {
            ArticleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return ArticleVoList;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo ArticleVo = new ArticleVo();
        ArticleVo.setId(String.valueOf(article.getId()));
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
        if(isBody){
           ArticleVo.setBody(findArticleBodyByID(article.getBodyId()));
        }
        if(isCategory){
            ArticleVo.setCategory(categoryService.findCategoryById(article.getCategoryId()));
        }
        return ArticleVo;
    }

    private ArticleBodyVo findArticleBodyByID(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
