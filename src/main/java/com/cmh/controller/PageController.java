package com.cmh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmh.dao.ArticleRepository;
import com.cmh.dao.CategoryRepository;
import com.cmh.dao.NewsRepository;
import com.cmh.dao.SourceRepository;

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
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }
}
