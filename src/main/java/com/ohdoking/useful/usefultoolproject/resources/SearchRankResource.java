package com.ohdoking.useful.usefultoolproject.resources;

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
public class SearchRankResource {
	
	@Autowired
	private SearchRankRepository searchRankRepository;
	
	@GetMapping
	public Page<SearchRank> retrieveAllSearchRanks(Pageable pageable) {
		return searchRankRepository.findAll(pageable);
	}
	
	@GetMapping("/detail")
	public List<SearchRank> retrieveSearchRanksBySearchWord(@RequestParam String searchWord, Sort sort) {
		return searchRankRepository.findBysearchWordLike(searchWord, sort);
	}
	
	
	@GetMapping("/{id}")
	public SearchRank retrieveSearchRank(@PathVariable Long id) {
		return searchRankRepository.findById(id)
				.orElseThrow(() -> new SearchRankNotFoundException(id));
	}
	
	@PostMapping
	public SearchRank newSearchRank(@RequestBody SearchRank searchRank) {
		return searchRankRepository.save(searchRank);
	}
	
	@PutMapping("/{id}")
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
	public void deleteEmployee(@PathVariable Long id) {
		searchRankRepository.deleteById(id);
	}

}
