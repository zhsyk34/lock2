package com.dnake.dao;

import com.dnake.entity.Word;
import org.junit.Test;

public class WordDaoTest extends BaseDaoTest {
	@Test
	public void save() throws Exception {
		for (long i = 1; i < 4; i++) {
			Word word = new Word().setLockId(i).setNumber((int) i % 3).setValue("133244");
			wordDao.save(word);
		}

	}

	@Test
	public void find() throws Exception {
		System.out.println(wordDao.find(1L, 2));
	}

	@Test
	public void findList() throws Exception {
		wordDao.findList().forEach(System.out::println);
	}

}