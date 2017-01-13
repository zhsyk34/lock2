package com.dnake.dao;

import com.dnake.entity.Word;

import java.util.List;

public interface WordDao extends CommonDao<Word, Long> {

	Word find(long lockId, int number);

	List<Word> findList(long lockId, Integer number);
}
