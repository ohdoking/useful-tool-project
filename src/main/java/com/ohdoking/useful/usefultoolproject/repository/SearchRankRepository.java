package com.ohdoking.useful.usefultoolproject.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ohdoking.useful.usefultoolproject.dto.SearchRank;

/**
 * JpaRepository extends PagingAndSortingRepository which in turn extends CrudRepository interface. 
 * So, JpaRepository inherits all the methods from the two interfaces shown below.
 * 
 * @author ohdoking
 *
 */
public interface SearchRankRepository extends JpaRepository<SearchRank, Long> {
	
	/**
	 * search like formula using searchWord 
	 * @param searchWord
	 * @param sort
	 * @return
	 */
	List<SearchRank> findBysearchWordLike(String searchWord, Sort sort);
}
