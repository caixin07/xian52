package com.xian52.service;

import java.util.List;

import org.apache.ibatis.annotations.Lang;

import com.xian52.domain.Image;
import com.xian52.domain.News;
import com.xian52.domain.Pic;

public interface PicService {
	public void insert(Pic pic);
	public Pic getOneByUrl(Pic pic);
	public List<Pic> getPicList();
	public List<Pic> getChildPicList(Pic pic);
	public List<Pic> getPicListRandom();

}
