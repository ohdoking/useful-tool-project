package com.ohdoking.useful.usefultoolproject.repository;

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
}
