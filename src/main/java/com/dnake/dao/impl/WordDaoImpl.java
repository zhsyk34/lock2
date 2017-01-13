package com.dnake.dao.impl;

import com.dnake.dao.WordDao;
import com.dnake.entity.Word;
import com.dnake.kit.ValidateKit;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WordDaoImpl extends CommonDaoImpl<Word, Long> implements WordDao {
	@Override
	public Word find(long lockId, int number) {
		List<Word> list = this.findList(lockId, number);
		return ValidateKit.empty(list) ? null : list.get(0);
	}

	@Override
	public List<Word> findList(long lockId, Integer number) {
		Map<String, Object> map = new HashMap<>();
		map.put("lockId", lockId);
		if (number != null && number > -1) {
			map.put("number", number);
		}
		return super.findList(map);
	}
}
