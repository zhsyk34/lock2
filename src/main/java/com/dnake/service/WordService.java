package com.dnake.service;

import com.dnake.entity.Word;

import java.util.List;

public interface WordService extends CommonService<Word, Long> {

	int update(long lockId, int number, String value);

	Word find(long lockId, int number);

	List<Word> findList(long lockId);
}
