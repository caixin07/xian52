package com.xian52.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xian52.domain.Image;
import com.xian52.domain.News;
import com.xian52.mapper.ImageMapper;
import com.xian52.mapper.NewsMapper;
import com.xian52.service.ImageService;
@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageMapper imageMapper;
	@Override
	public void insert(Image image) {		
		imageMapper.insert(image);
	}
	@Override
	public List<Image> getImageByNews(Image image) {
		// TODO Auto-generated method stub
		return imageMapper.getImageByNews(image);
	}

}
