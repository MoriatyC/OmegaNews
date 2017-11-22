package com.cmh.controller;

import java.util.List;

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
import com.cmh.model.Article;
import com.cmh.model.Category;
import com.cmh.model.News;

@Controller
public class PageController {
  
    private CategoryRepository categoryRepository;
    private NewsRepository newsRepository;
    private ArticleRepository articleRepository;
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
        List<News> newsList = newsRepository.findFirst10AllByOrderByPtimeDesc();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("newsList", newsList);
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
    @RequestMapping("/article/{docid}")
    public String article( Model model, @PathVariable("docid") String docid) {
        News news = newsRepository.findByDocid(docid);
        Article article = articleRepository.findByUrl(news.getArticleId().getUrl());
        String content = article.getContent().replace("class=\"content\"", "class=\"blog-post\"");
        model.addAttribute("content", content);
        return "article";
    }
}
