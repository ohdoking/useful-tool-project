package com.ohdoking.useful.usefultoolproject.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	/**
	 * search using Date
	 * 
	 * @param startDate startDate
	 * @param finishDate finishDate
	 * @param page
	 * @return
	 */
	Page<SearchRank> findByDateBetween(Timestamp startDate, Timestamp finishDate, Pageable page);
}
