package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

public interface CarouseService {
    List<Carousel> queryAll(Integer isShow);
}
