package com.xian52.service;

import java.util.List;

import org.apache.ibatis.annotations.Lang;

import com.xian52.domain.Image;
import com.xian52.domain.News;

public interface ImageService {
	public void insert(Image image);
	public List<Image> getImageByNews(Image image);
}
