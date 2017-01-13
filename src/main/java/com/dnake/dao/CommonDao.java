package com.dnake.dao;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CommonDao<Entity, Id extends Serializable> {

	SqlSession session();

	int save(Entity entity);

	int saves(Collection<Entity> entities);

	int deleteById(Id id);

	int deleteByEntity(Entity entity);

	int deleteByIds(Id[] ids);

	int deleteByIds(Collection<Id> ids);

	int deleteByEntities(Collection<Entity> entities);

	int update(Entity entity);

	int updates(Collection<Entity> entities);

	Entity findById(Id id);

	<T> List<T> findList(Map<String, ?> query, Sort sort, Page page);

	<T> List<T> findList(Map<String, ?> query);

	List<Entity> findList(Sort sort, Page page);

	List<Entity> findList();

	int count(Map<String, ?> query);

	int count();

}
