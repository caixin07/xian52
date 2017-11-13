package com.xian52.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xian52.service.BaiduSendService;
import com.xian52.service.OpgirlCrawler;

@Component
public class ScheduledCrawler {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledCrawler.class);


	@Autowired
	private OpgirlCrawler opgirlCrawler;
	@Autowired
	private BaiduSendService baiduSendService;



	@Scheduled(cron = "0 0/10 6-23 * * ?")
	public void executeBaiduSendTask() throws IOException {
		logger.info("ScheduledTest.executeMydriversCrawler 定时任务baiduSendTask");
		try {
			baiduSendService.baiduSendTask();
		} catch (Exception e) {
			logger.error("定时任务baiduSendService异常",e);
		}
	}

}
