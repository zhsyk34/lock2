package com.dnake.service.impl;

import com.dnake.dao.CommonDao;
import com.dnake.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;

class CommonServiceImpl<Entity, Id extends Serializable> implements CommonService<Entity, Id> {

	@Autowired
	private CommonDao<Entity, Id> commonDao;

	@Override
	public int save(Entity entity) {
		return commonDao.save(entity);
	}

	@Override
	public int saves(Collection<Entity> entities) {
		return commonDao.saves(entities);
	}

	@Override
	public int deleteById(Id id) {
		return commonDao.deleteById(id);
	}

	@Override
	public int deleteByEntity(Entity entity) {
		return commonDao.deleteByEntity(entity);
	}

	@Override
	public int deleteByIds(Id[] ids) {
		return commonDao.deleteByIds(ids);
	}

	@Override
	public int deleteByIds(Collection<Id> ids) {
		return commonDao.deleteByIds(ids);
	}

	@Override
	public int deleteByEntities(Collection<Entity> entities) {
		return commonDao.deleteByEntities(entities);
	}

	@Override
	public int update(Entity entity) {
		return commonDao.update(entity);
	}

	@Override
	public int updates(Collection<Entity> entities) {
		return commonDao.updates(entities);
	}

	@Override
	public Entity findById(Id id) {
		return commonDao.findById(id);
	}

}
