package com.ohdoking.useful.usefultoolproject.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ohdoking.useful.usefultoolproject.dto.SearchRank;
import com.ohdoking.useful.usefultoolproject.exception.SearchRankNotFoundException;
import com.ohdoking.useful.usefultoolproject.repository.SearchRankRepository;

@RestController
public class SearchRankResource {
	
	@Autowired
	private SearchRankRepository searchRankRepository;
	
	@GetMapping("/searchRanks")
	public List<SearchRank> retrieveAllSearchRanks() {
		return searchRankRepository.findAll();
	}
	
	
	@GetMapping("/searchRanks/{id}")
	public SearchRank retrieveSearchRank(@PathVariable Long id) {
		return searchRankRepository.findById(id)
				.orElseThrow(() -> new SearchRankNotFoundException(id));
	}
	
	@PostMapping("/searchRanks")
	public SearchRank newSearchRank(@RequestBody SearchRank searchRank) {
		return searchRankRepository.save(searchRank);
	}
	
	@PutMapping("/searchRanks/{id}")
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

	@DeleteMapping("/searchRanks/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		searchRankRepository.deleteById(id);
	}

}
