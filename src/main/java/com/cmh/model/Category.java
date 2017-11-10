package com.cmh.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    private String categoryCode;
    private String categoryName;
}
