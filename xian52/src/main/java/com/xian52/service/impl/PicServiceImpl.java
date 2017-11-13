package com.xian52.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xian52.domain.Pic;
import com.xian52.mapper.PicMapper;
import com.xian52.service.PicService;
@Service
public class PicServiceImpl implements PicService {
	@Autowired
	private PicMapper picMapper;
	@Override
	public void insert(Pic pic) {		
		picMapper.insert(pic);
	}
	@Override
	public Pic getOneByUrl(Pic pic) {
		return picMapper.getOneByUrl(pic);
	}
	@Override
	public List<Pic> getPicList() {
		return picMapper.getPicList();
	}
	@Override
	public List<Pic> getChildPicList(Pic pic) {
		return picMapper.getChildPicList(pic);

	}
	@Override
	public List<Pic> getPicListRandom() {
		// TODO Auto-generated method stub
		return picMapper.getPicListRandom();
	}

}
