package com.cmh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmh.dao.ArticleRepository;
import com.cmh.dao.CategoryRepository;
import com.cmh.dao.NewsRepository;
import com.cmh.dao.SourceRepository;
import com.cmh.model.Category;
import com.cmh.model.News;

@Controller
public class PageController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    SourceRepository sourceRepository;
    @RequestMapping("/index")
    public String index(Model model) {
        List<News> newsList = newsRepository.findAll(); 
        model.addAttribute("newsList", newsList);
        return "index";
    }
    @RequestMapping("/category")
    public String category(Model model, String id) {
        Category category = categoryRepository.findByCategoryName(id);
        List<News> newsList = newsRepository.findFirst5ByCategoryCode(category);
        for (News i : newsList) {
            System.out.println(i.getDocid());
        }
        model.addAttribute("newsList", newsList);
        model.addAttribute("category", category);
        return "category";
    }
}
