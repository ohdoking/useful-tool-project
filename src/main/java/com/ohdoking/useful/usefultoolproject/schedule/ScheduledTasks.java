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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ohdoking.useful.usefultoolproject.dto.SearchRank;
import com.ohdoking.useful.usefultoolproject.repository.SearchRankRepository;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
	private SearchRankRepository searchRankRepository;
    
    /**
     * Naver(http://www.naver.com) Top 20 research word crawling 
     */
    //the top of every hour of every day.
    @Scheduled(cron = "0 0 * * * *")
    public void reportCurrentTime() {
    	String crwaObjectUrl = "http://www.naver.com";
    	
    	List<SearchRank> items = new ArrayList<SearchRank>();

        try {
        	log.info("start crawling {} {}", crwaObjectUrl,  dateFormat.format(new Date()));
        	Connection connection = Jsoup.connect(crwaObjectUrl);
        	Document htmlDocument = connection.get();
        	Elements searchRankDivs = htmlDocument.getElementsByClass("ah_roll_area");
        	for(Element searchRankDiv : searchRankDivs) {
        		for(Element searchRanks : searchRankDiv.getElementsByClass("ah_item")) {
        			String rank = searchRanks.getElementsByClass("ah_r").get(0).text();
        			String searchWord = searchRanks.getElementsByClass("ah_k").get(0).text();
        			
        			SearchRank temp = new SearchRank();
        			temp.setRank(rank);
        			temp.setSearchWord(searchWord);
        			temp.setSource(crwaObjectUrl);
        			
        			items.add(temp);
        		}
        		
        	}
        	
        	searchRankRepository.saveAll(items);
        	
		} catch (Exception e) {
			log.error("occur during crawling {} {}",crwaObjectUrl,  dateFormat.format(new Date()));
		}
        
    }
}