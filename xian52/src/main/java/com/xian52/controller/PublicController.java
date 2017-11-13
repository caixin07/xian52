package com.xian52.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageHelper;
import com.xian52.domain.Image;
import com.xian52.domain.News;
import com.xian52.domain.Pic;
import com.xian52.service.ImageService;
import com.xian52.service.NewsService;
import com.xian52.service.PicService;
import com.xian52.util.Constants;
import com.xian52.util.HTMLSpirit;

@Service
public class PublicController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private PicService picService;
	public void pub(ModelMap map, HttpServletRequest request,String type) throws IOException {
		int pageNum = 1;
		PageHelper.startPage(pageNum, 4);
		List<News> list = newsService.getAllForTuijian();
		for (News news : list) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());
			
			//获取图片
			Image image = new Image();
			image.setNews_id(news.getId());
			List<Image> images = imageService.getImageByNews(image);
			news.setImages(images);
		}
		
		
		
		
		//导航
		StringBuffer m_bar = new StringBuffer();
		StringBuffer pc_bar = new StringBuffer();
		if(type.equals(Constants.TYPE_SH)){
			m_bar.append("<li><a href='/'>推荐</a></li><li class='active'><a href='/sh/'>社会</a></li><li><a href='/pic/'>发展</a></li><li><a href='/fw/'>房屋</a></li><li><a href='/jt/'>交通</a></li><li><a href='/jy/'>教育</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item'><span>推荐</span></a></li><li><a href='/sh/' target='_self' ga_event='channel_hot_click' class='channel-item active'><span>社会</span></a></li><li><a href='/fz/' target='_self' ga_event='channel_video_click' class='channel-item'><span>发展</span></a></li><li><a href='/fw/' target='_self' ga_event='channel_social_click' class='channel-item'><span>房屋</span></a></li></li><li><a href='/jt/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>交通</span></a></li></li><li><a href='/jy/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>教育</span></a></li>");
		}else if(type.equals(Constants.TYPE_FZ)){
			m_bar.append("<li><a href='/'>推荐</a></li><li><a href='/sh/'>社会</a></li><li  class='active'><a href='/pic/'>发展</a></li><li><a href='/fw/'>房屋</a></li><li><a href='/jt/'>交通</a></li><li><a href='/jy/'>教育</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item'><span>推荐</span></a></li><li><a href='/sh/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>社会</span></a></li><li><a href='/fz/' target='_self' ga_event='channel_video_click' class='channel-item active'><span>发展</span></a></li><li><a href='/fw/' target='_self' ga_event='channel_social_click' class='channel-item'><span>房屋</span></a></li></li><li><a href='/jt/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>交通</span></a></li></li><li><a href='/jy/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>教育</span></a></li>");
		}else if(type.equals(Constants.TYPE_FW)){
			m_bar.append("<li><a href='/'>推荐</a></li><li class='active'><a href='/sh/'>社会</a></li><li><a href='/pic/'>发展</a></li><li class='active'><a href='/fw/'>房屋</a></li><li><a href='/jt/'>交通</a></li><li><a href='/jy/'>教育</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item'><span>推荐</span></a></li><li><a href='/sh/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>社会</span></a></li><li><a href='/fz/' target='_self' ga_event='channel_video_click' class='channel-item'><span>发展</span></a></li><li><a href='/fw/' target='_self' ga_event='channel_social_click' class='channel-item active'><span>房屋</span></a></li></li><li><a href='/jt/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>交通</span></a></li></li><li><a href='/jy/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>教育</span></a></li>");
		}else if(type.equals(Constants.TYPE_JT)){
			m_bar.append("<li><a href='/'>推荐</a></li><li class='active'><a href='/sh/'>社会</a></li><li><a href='/pic/'>发展</a></li><li><a href='/fw/'>房屋</a></li><li class='active'><a href='/jt/'>交通</a></li><li><a href='/jy/'>教育</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item'><span>推荐</span></a></li><li><a href='/sh/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>社会</span></a></li><li><a href='/fz/' target='_self' ga_event='channel_video_click' class='channel-item'><span>发展</span></a></li><li><a href='/fw/' target='_self' ga_event='channel_social_click' class='channel-item'><span>房屋</span></a></li></li><li><a href='/jt/' target='_self' ga_event='channel_hot_click' class='channel-item active'><span>交通</span></a></li></li><li><a href='/jy/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>教育</span></a></li>");	
		}else if(type.equals(Constants.TYPE_JY)){
			m_bar.append("<li><a href='/'>推荐</a></li><li class='active'><a href='/sh/'>社会</a></li><li><a href='/pic/'>发展</a></li><li><a href='/fw/'>房屋</a></li><li><a href='/jt/'>交通</a></li><li class='active'><a href='/jy/'>教育</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item'><span>推荐</span></a></li><li><a href='/sh/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>社会</span></a></li><li><a href='/fz/' target='_self' ga_event='channel_video_click' class='channel-item'><span>发展</span></a></li><li><a href='/fw/' target='_self' ga_event='channel_social_click' class='channel-item'><span>房屋</span></a></li></li><li><a href='/jt/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>交通</span></a></li></li><li><a href='/jy/' target='_self' ga_event='channel_hot_click' class='channel-item active'><span>教育</span></a></li>");	
		}
		else{
			m_bar.append("<li class='active'><a href='/'>推荐</a></li><li class='active'><a href='/sh/'>社会</a></li><li><a href='/pic/'>发展</a></li><li><a href='/fw/'>房屋</a></li><li><a href='/jt/'>交通</a></li><li><a href='/jy/'>教育</a></li>");
			pc_bar.append("<li><a href='/' target='_self' ga_event='channel_recommand_click' class='channel-item active'><span>推荐</span></a></li><li><a href='/sh/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>社会</span></a></li><li><a href='/fz/' target='_self' ga_event='channel_video_click' class='channel-item'><span>发展</span></a></li><li><a href='/fw/' target='_self' ga_event='channel_social_click' class='channel-item'><span>房屋</span></a></li></li><li><a href='/jt/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>交通</span></a></li></li><li><a href='/jy/' target='_self' ga_event='channel_hot_click' class='channel-item'><span>教育</span></a></li>");
		}

		map.put("m_bar", m_bar);
		map.put("pc_bar", pc_bar);

		map.put("pubNewsList", list);
		map.put("keys_", "西安发布,西安新闻,西安建设、西安发展、西安社会、西安教育、西安交通、西安房屋");
		map.put("des", "西安发布,发布西安咨询,聚焦西安新闻、建设、发展、社会、教育、交通、房屋等等");
		map.put("title", "西安发布网-西安建设、西安发展、西安社会、西安教育、西安交通、西安房屋");
	}
}
