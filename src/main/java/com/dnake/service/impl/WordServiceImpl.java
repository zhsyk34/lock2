package com.dnake.service.impl;

import com.dnake.dao.WordDao;
import com.dnake.entity.Word;
import com.dnake.service.WordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WordServiceImpl extends CommonServiceImpl<Word, Long> implements WordService {
	@Resource
	private WordDao wordDao;

	@Override
	public int update(long lockId, int number, String value) {
		Word word = this.find(lockId, number);
		if (word == null) {
			return 0;
		}
		word.setValue(value);
		return wordDao.update(word);
	}

	@Override
	public Word find(long lockId, int number) {
		return wordDao.find(lockId, number);
	}

	@Override
	public List<Word> findList(long lockId) {
		return wordDao.findList(lockId, -1);
	}
}
