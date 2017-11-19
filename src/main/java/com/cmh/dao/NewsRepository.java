package com.cmh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmh.model.Category;
import com.cmh.model.News;

public interface NewsRepository extends JpaRepository<News, String>{
    News findByDocid(String docid);
    List<News> findFirst5ByCategoryCode(Category category);
    
    
    
//    List<News> findFirst5ByHasImg(int hasImg);
//    List<News> findAllOrderByPtimeDesc();
//    List<News> findFirst10All();
//    List<News> findAllOrderByPtime();

}
