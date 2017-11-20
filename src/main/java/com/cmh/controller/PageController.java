package com.cmh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmh.dao.CategoryRepository;
import com.cmh.dao.NewsRepository;
import com.cmh.model.Category;
import com.cmh.model.News;

@Controller
public class PageController {
  
    private CategoryRepository categoryRepository;
    private NewsRepository newsRepository;
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
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

}
