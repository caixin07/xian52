package com.xian52.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.xian52.domain.Image;
import com.xian52.domain.News;
import com.xian52.domain.Pic;
@Mapper
public interface PicMapper {
	public void insert(Pic pic);
	public Pic getOneByUrl(Pic pic);
	public List<Pic> getPicList();
	public List<Pic> getChildPicList(Pic pic);
	public List<Pic> getPicListRandom();
}
