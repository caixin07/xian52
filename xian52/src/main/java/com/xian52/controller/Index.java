package com.xian52.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xian52.domain.Image;
import com.xian52.domain.News;
import com.xian52.domain.Pic;
import com.xian52.mapper.NewsMapper;
import com.xian52.service.ImageService;
import com.xian52.service.NewsService;
import com.xian52.service.OpgirlCrawler;
import com.xian52.service.PicService;
import com.xian52.util.Constants;
import com.xian52.util.HTMLSpirit;

@Controller
@EnableAutoConfiguration
public class Index {
	Logger logger = LoggerFactory.getLogger(Index.class);  
	@Autowired
	private NewsService newsService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private PicService picService;
	@Autowired
	private PublicController publicController;
	@Value("${page.pageSize}")
	private int pageSize;
	@Autowired
	private OpgirlCrawler opgirlCrawler;

	@RequestMapping("/test")
	public void test() throws Exception {
		opgirlCrawler.getNews();
	}

	@RequestMapping("/showNews")
	public List<News> showNews() throws IOException {
		List<News> list = newsService.getAll();
		return newsService.getAll();
	}

	@RequestMapping("/page/{pageNum}")
	public String showNews(ModelMap map, @PathVariable Integer pageNum, HttpServletRequest request, String type)
			throws IOException {
		News select = new News();
		select.setType(type);

		pageNum = pageNum == null ? 1 : pageNum;
		PageHelper.startPage(pageNum, pageSize);
		List<News> list = newsService.getListByType(select);//获取新闻
		

		
		
		for (News news : list) {
			news.setText(HTMLSpirit.delHTMLTag(news.getText()));
			news.setHref(request.getAttribute("basePath") + "/id/" + news.getId());

			Image image = new Image();
			image.setNews_id(news.getId());
			List<Image> images = imageService.getImageByNews(image);
			news.setImages(images);
		}

		PageInfo pageInfo = new PageInfo(list);
		long total = pageInfo.getPages();
		long nextPage = pageInfo.getNextPage();

		if (type == null) {
			type = "page";
		}

		// 生成页码
		StringBuffer pageList = new StringBuffer();

		int startNum = pageNum - 3 <= 0 ? 1 : pageNum - 3;
		int endNum = pageNum + 3 >= (int) total ? (int) total : pageNum + 3;

		//上一页
		String lastPage = "1";
		if(pageNum > 1){
			lastPage = (pageNum - 1) + "";
		}

		pageList.append("<li><a href='" + request.getAttribute("basePath") + "/" + type + "/" + lastPage
				+ "' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");

		for (int i = startNum; i < pageNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/" + type + "/" + i + "'>" + i + " <span class='sr-only'>(current)</span></a></li>");
		}
		pageList.append("<li class='active'><a href='" + request.getAttribute("basePath") + "/" + type + "/" + pageNum + "'>"+pageNum+" <span class='sr-only'>(current)</span></a></li>");
		for (int i = pageNum + 1; i <= endNum; i++) {
			pageList.append("<li><a href='" + request.getAttribute("basePath") + "/" + type + "/" + i + "'>" + i + " <span class='sr-only'>(current)</span></a></li>");
		}
		pageList.append("<li><a href='" + request.getAttribute("basePath") + "/" + type + "/"
				+ nextPage + "' aria-label='Next'> <span aria-hidden='true'>&raquo;</span></a></li>");

		// 总页数
		map.put("total", total);
		map.put("nextPage", nextPage);
		map.put("pageList", pageList.toString());
		map.put("newsList", list);
		publicController.pub(map, request,type);

		return "index";
	}

	@RequestMapping("/id/{id}")
	public String getOne(ModelMap map, @PathVariable String id, HttpServletRequest request) throws IOException {
		int d = Integer.parseInt(id);
		News news = newsService.getOne(d);

		Image image = new Image();
		image.setNews_id(news.getId());
		List<Image> images = imageService.getImageByNews(image);
		news.setImages(images);

		String count = news.getCount();
		String count_ = news.getCount_();
		int c = Integer.parseInt(count);
		c = c + 1;
		count = c + "";
		news.setCount(count);

		int e = Integer.parseInt(count_);
		e = e + 1;
		count_ = e + "";
		news.setCount_(count_);
		newsService.updateCount(news);

		publicController.pub(map, request,"");

		map.put("news", news);
		if (null != news.getKeys_() && !"".equals(news.getKeys_())) {
			map.put("keys_", news.getKeys_());
		}
		if (null != news.getTitle() && !"".equals(news.getTitle())) {
			map.put("des", news.getTitle());
			map.put("title", news.getTitle() + "—西安发布网-西安建设、西安发展、西安社会、西安教育、西安交通、西安房屋");
		}
		if(Constants.URL_FROM_WX.equals(news.getUrl_from())){
			return "index_info_wx";
		}else {
			return "index_info";
		}
	}

	@RequestMapping("/")
	String Index(ModelMap map, HttpServletRequest request) throws IOException {
		showNews(map, 1, request, null);
		return "index";
	}

	@RequestMapping("/sh")
	String liangxing(ModelMap map, HttpServletRequest request) throws IOException {
		return showNews(map, 1, request, Constants.TYPE_SH);
	}

	@RequestMapping("/sh/{pageNum}")
	String liangxing(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		return showNews(map, pageNum, request, Constants.TYPE_SH);
	}

	@RequestMapping("/fz")
	String keji(ModelMap map, HttpServletRequest request) throws IOException {
		return showNews(map, 1, request, Constants.TYPE_FZ);
	}

	@RequestMapping("/fz/{pageNum}")
	String keji(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		return showNews(map, pageNum, request, Constants.TYPE_FZ);
	}
	@RequestMapping("/fw")
	String fangwu(ModelMap map, HttpServletRequest request) throws IOException {
		return showNews(map, 1, request, Constants.TYPE_FW);
	}

	@RequestMapping("/fw/{pageNum}")
	String fangwu(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		return showNews(map, pageNum, request, Constants.TYPE_FW);
	}
	@RequestMapping("/jt")
	String jiaotong(ModelMap map, HttpServletRequest request) throws IOException {
		return showNews(map, 1, request, Constants.TYPE_JT);
	}

	@RequestMapping("/jt/{pageNum}")
	String jiaotong(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		return showNews(map, pageNum, request, Constants.TYPE_JT);
	}
	@RequestMapping("/jy")
	String jiaoyu(ModelMap map, HttpServletRequest request) throws IOException {
		return showNews(map, 1, request, Constants.TYPE_JY);
	}

	@RequestMapping("/jy/{pageNum}")
	String jiaoyu(ModelMap map, HttpServletRequest request, @PathVariable Integer pageNum) throws IOException {
		return showNews(map, pageNum, request, Constants.TYPE_JY);
	}
}
