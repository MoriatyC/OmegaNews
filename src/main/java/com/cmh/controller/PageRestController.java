package com.cmh.controller;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmh.dao.NewsRepository;
import com.cmh.dao.RedisDao;
import com.cmh.domain.News;

@RestController
public class PageRestController {
    private final long ONE_WEEK_IN_SECONDS = 7 * 86400;
    private final int VOTE_SCORE = 432;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private NewsRepository newsRepository;
    
    @RequestMapping("/rank/")
    public List<News> updateRank() {
        List<News> list = newsRepository.findFirst100AllByOrderByPtimeDesc();
        for (News item: list) {
            Map<String, Object> map = new HashMap<>();
                map.put("commentCount", String.valueOf(item.getCommentCount()));
                map.put("title", item.getTitle());
                map.put("url", item.getArticleId().getUrl());
                map.put("imgsrc", item.getImgsrc());
                map.put("digest", item.getDigest());
            redisDao.hset(MessageFormat.format("news:{0}", item.getDocid()), map);
        }
        return list;
    }
    @RequestMapping("/getrank/")
    public Set<News> rank() {
        Set rank = redisDao.getTop("score", 30);
        return rank;
    }
    
    @RequestMapping("/upvote/{docid}/")
    public String upvote(@PathVariable("docid") String docid) {
        StringBuilder sb = new StringBuilder();
        String key = sb.append("news:").append(docid).toString();
        Integer count = Integer.valueOf(redisDao.hget(key, "commentCount").toString());
        count++;
        redisDao.hsetValue(key, "commentCount", count.toString());
        return "successful";
    }
}
