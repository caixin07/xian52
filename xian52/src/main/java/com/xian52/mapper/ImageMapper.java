package com.xian52.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.xian52.domain.Image;
import com.xian52.domain.News;
@Mapper
public interface ImageMapper {
	public void insert(Image image);
	public List<Image> getImageByNews(Image image);
}
