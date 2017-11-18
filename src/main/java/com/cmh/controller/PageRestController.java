package com.cmh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageRestController {
    @RequestMapping("/haha")
    public String test() {
        return "asdf";
    }
}
