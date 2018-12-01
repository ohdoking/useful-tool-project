package com.ohdoking.useful.usefultoolproject.exception;

public class SearchRankNotFoundException extends RuntimeException {

	public SearchRankNotFoundException(Long id) {
		super("Could not find searchRank " + id);
	}
}

