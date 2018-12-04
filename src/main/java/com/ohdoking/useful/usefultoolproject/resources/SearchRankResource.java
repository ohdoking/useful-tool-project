package com.ohdoking.useful.usefultoolproject.resources;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ohdoking.useful.usefultoolproject.dto.SearchRank;
import com.ohdoking.useful.usefultoolproject.exception.SearchRankNotFoundException;
import com.ohdoking.useful.usefultoolproject.repository.SearchRankRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/searchRanks" )
@Api(value = "SearchRankResource", description = "API related in SearchRank",basePath = "/searchRanks")
public class SearchRankResource {
	
	@Autowired
	private SearchRankRepository searchRankRepository;
	
	@GetMapping
	@ApiOperation(value = "retrieve All Search Ranks List", notes = "API that retrieve All Search Ranks List.")
	public Page<SearchRank> retrieveAllSearchRanks(Pageable pageable) {
		return searchRankRepository.findAll(pageable);
	}
	
	/**
	 * retrieve Specific date period Search Ranks List!
	 * 
	 * @param pageable
	 * @param startDate startDate(format : yyyyMMDD)
	 * @param finishDate finishDate(format : yyyyMMDD)
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/search")
	@ApiOperation(value = "retrieve Specific date period Search Ranks List", notes = "API that retrieve Specific date period Search Ranks List.")
	public Page<SearchRank> retrieveAllSearchRanksWithConditionDate(
			Pageable pageable, 
			@RequestParam(defaultValue = "19700101") String startDate, 
			@RequestParam(defaultValue = "29001230") String finishDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return searchRankRepository.findByDateBetween(new Timestamp(formatter.parse(startDate).getTime()), new Timestamp(formatter.parse(finishDate).getTime()), pageable);
	}
	
	@GetMapping("/detail")
	@ApiOperation(value = "retrieve Search Ranks List by searchword", notes = "API that retrieve All Search Ranks List by searchword.")
	public List<SearchRank> retrieveSearchRanksBySearchWord(@RequestParam String searchWord, Sort sort) {
		return searchRankRepository.findBysearchWordLike(searchWord, sort);
	}
	
	
	@GetMapping("/{id}")
	@ApiOperation(value = "retrieve SearchRank Item by id", notes = "API that retrieve SearchRank Item by id.")
	public SearchRank retrieveSearchRank(@PathVariable Long id) {
		return searchRankRepository.findById(id)
				.orElseThrow(() -> new SearchRankNotFoundException(id));
	}
	
	@PostMapping
	@ApiOperation(value = "save new SearchRank Item", notes = "API that save new SearchRank Item.")
	public SearchRank newSearchRank(@RequestBody SearchRank searchRank) {
		return searchRankRepository.save(searchRank);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "replace new SearchRank Item", notes = "API that replace new SearchRank Item.")
	public SearchRank replaceEmployee(@RequestBody SearchRank newSearchRank, @PathVariable Long id) {

		return searchRankRepository.findById(id)
			.map(searchRank -> {
				
				searchRank.setRank(newSearchRank.getRank());
				searchRank.setSearchWord(newSearchRank.getSearchWord());
				searchRank.setSource(newSearchRank.getSource());
				
				return searchRankRepository.save(searchRank);
			})
			.orElseGet(() -> {
				newSearchRank.setId(id);
				return searchRankRepository.save(newSearchRank);
			});
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "delete new SearchRank Item", notes = "API that delete new SearchRank Item.")
	public void deleteEmployee(@PathVariable Long id) {
		searchRankRepository.deleteById(id);
	}

}
