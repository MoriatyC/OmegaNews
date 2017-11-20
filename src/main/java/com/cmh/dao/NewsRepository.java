package com.cmh.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cmh.model.Category;
import com.cmh.model.News;

public interface NewsRepository extends JpaRepository<News, String>{
    News findByDocid(String docid);
    List<News> findFirst10AllByOrderByPtimeDesc();
    Page<News> findByCategoryCodeOrderByPtimeDesc(Category category, Pageable pageable);
}
