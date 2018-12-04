package com.ohdoking.useful.usefultoolproject.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ohdoking.useful.usefultoolproject.dto.SearchRank;
import com.ohdoking.useful.usefultoolproject.repository.SearchRankRepository;

/**
 * 
 * for Scheduling class 
 * 
 * how to define cron.
 * 
 *  "0 0 * * * *" = the top of every hour of every day.
 *  *"/10 * * * * *" = every ten seconds.
 *	"0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
 *	"0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
 *	"0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
 *	"0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
 *	"0 0 0 25 12 ?" = every Christmas Day at midnight
 *
 * @author ohdoking
 *
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
	private SearchRankRepository searchRankRepository;
    
    @Autowired
    private Environment env;
    
    /**
     * Naver(http://www.naver.com) Top 20 research word crawling every 1 hour
     */
    //the top of every hour of every day.
    @Scheduled(cron = "0 0 * * * *")
    //@Scheduled(fixedRate = 50000)
    public void reportCurrentTime() {
    	String crwaObjectUrl = env.getProperty("crawl.url");
    	
    	List<SearchRank> items = new ArrayList<SearchRank>();

        try {
        	log.info("start crawling {} {}", crwaObjectUrl,  dateFormat.format(new Date()));
        	
        	Connection connection = Jsoup.connect(crwaObjectUrl);
        	Document htmlDocument = connection.get();
        	
        	Elements searchRankDivs = htmlDocument.getElementsByClass(env.getProperty("crawl.searchrankdiv"));
        	for(Element searchRankDiv : searchRankDivs) {
        		for(Element searchRanks : searchRankDiv.getElementsByClass(env.getProperty("crawl.searchrankdiv.item"))) {
        			String rank = searchRanks.getElementsByClass(env.getProperty("crawl.searchrankdiv.item.rank")).get(0).text();
        			String searchWord = searchRanks.getElementsByClass(env.getProperty("crawl.searchrankdiv.item.searchword")).get(0).text();
        			
        			SearchRank temp = new SearchRank();
        			temp.setRank(rank);
        			temp.setSearchWord(searchWord);
        			temp.setSource(crwaObjectUrl);
        			
        			items.add(temp);
        		}
        		
        	}
        	
        	searchRankRepository.saveAll(items);
        	
        	log.info("end crawling {} {}", crwaObjectUrl,  dateFormat.format(new Date()));
        	
		} catch (Exception e) {
			log.error("occur during crawling {} {}",crwaObjectUrl,  dateFormat.format(new Date()));
		}
        
    }
}