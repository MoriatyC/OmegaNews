package com.cmh.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@NoArgsConstructor
@AllArgsConstructor
@Data
public class News {
    

    @Id
    private String docid;
    private String title;
    private Source sourceId;
    private String imgsrc;
    private int priority;
    private int hasImg;
    private String digest;
    private int commentCount;
    private Article articleId;    
    private Date ptime;
    private Category categoryCode;
    private Site siteId;
    
}
