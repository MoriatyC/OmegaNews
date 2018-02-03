package com.cmh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmh.dao.ArticleRepository;
import com.cmh.dao.CategoryRepository;
import com.cmh.dao.NewsRepository;
import com.cmh.dao.RedisDao;
import com.cmh.domain.Article;
import com.cmh.domain.Category;
import com.cmh.domain.News;

/**
 * @author cmh
 *
 */
@Controller
public class PageController {

    private CategoryRepository categoryRepository;
    private NewsRepository newsRepository;
    private ArticleRepository articleRepository;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    @RequestMapping("/")
    public String index(Model model) {
        StringBuilder sb = new StringBuilder();
        List<News> newsList = new ArrayList<>();;
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        
        Set timeRank = redisDao.getTop("time:", 10);
        for (Object o: timeRank) {
            String id = o.toString();
            News news = new News();
            news.setImgsrc(redisDao.hget(id, "imgsrc").toString());
            news.setCommentCount(Integer.valueOf(redisDao.hget(id, "commentCount").toString()));
            news.setTitle(redisDao.hget(id, "title").toString());
            news.setDigest(redisDao.hget(id, "digest").toString());
            news.setDocid(o.toString().split(":")[1]);
            newsList.add(news);
        }
        model.addAttribute("newsList", newsList);
        Set rankOrigin = redisDao.getTop("score:", 10);
        List<String> rank = new ArrayList<String>();
        
        for (Object o: rankOrigin) {
            String mapName = sb.append("news:").append(o.toString().split(":")[1]).toString();
            rank.add(redisDao.hget(mapName, "title").toString());
            sb.delete(0, sb.length());
        }
        model.addAttribute("hotNewsList", rank);
        return "index";
    }
    @RequestMapping("/category")
    public String category(Model model, String id, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(id);
        Page<News> newsPage = newsRepository.findByCategoryCodeOrderByPtimeDesc(category, pageable);
        PageWrapper<News> page = new PageWrapper<>(newsPage);
        model.addAttribute("page", page);
        model.addAttribute("newsList", page.getContent());
        model.addAttribute("category", category);
        return "category";
    }
    @RequestMapping("/single/{docid}")
    public String single( Model model, @PathVariable("docid") String docid) {
        News news = newsRepository.findByDocid(docid);
        Article article = articleRepository.findByUrl(news.getArticleId().getUrl());
        String content = article.getContent().replace("class=\"content\"", "class=\"blog-post\"");
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("content", content);
        return "single";
    }
    static int count = 0;
    @RequestMapping("/article/{docid}")
    public String article( Model model, @PathVariable("docid") String docid) {
        News news = newsRepository.findByDocid(docid);
        Article article = articleRepository.findByUrl(news.getArticleId().getUrl());
        String content = article.getContent().replace("class=\"content\"", "class=\"blog-post\"");
        model.addAttribute("content", content);
        return "article";
    }
}
