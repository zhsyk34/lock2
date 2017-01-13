package com.dnake.dao.impl;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.common.TemplateSession;
import com.dnake.dao.CommonDao;
import com.dnake.kit.BeanKit;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CommonDaoImpl<Entity, Id extends Serializable> implements CommonDao<Entity, Id> {
	private static final String SORT = "sort";
	private static final String ORDER = "order";

	@Resource
	protected TemplateSession session;

	@PostConstruct
	public void init() {
		Class<Entity> clazz = BeanKit.getGenericType(this.getClass());
		session.setPackageName(clazz.getName());
	}

	@Override
	public SqlSession session() {
		return this.session.session();
	}

	@Override
	public int save(Entity entity) {
		return session.insert(entity);
	}

	@Override
	public int saves(Collection<Entity> entities) {
		return session.insert(entities);
	}

	@Override
	public int deleteById(Id id) {
		return session.delete(id);
	}

	@Override
	public int deleteByEntity(Entity entity) {
		return session.delete(entity);
	}

	@Override
	public int deleteByIds(Id[] ids) {
		return session.delete(ids);
	}

	@Override
	public int deleteByIds(Collection<Id> ids) {
		return session.delete(ids.toArray());
	}

	@Override
	public int deleteByEntities(Collection<Entity> entities) {
		return session.delete(entities);
	}

	@Override
	public int update(Entity entity) {
		return session.update(entity);
	}

	@Override
	public int updates(Collection<Entity> entities) {
		int count = 0;
		for (Entity entity : entities) {
			count += this.update(entity);
		}
		return count;
	}

	@Override
	public Entity findById(Id id) {
		return session.selectOne(id);
	}

	@Override
	public <T> List<T> findList(Map<String, ?> query, Sort sort, Page page) {
		Map<String, Object> map = new HashMap<>();
		if (query != null) {
			map.putAll(query);
		}
		if (sort != null) {
			map.put(SORT, sort.column());
			map.put(ORDER, sort.order().toString());
		}
		return page == null ? session.selectList(map) : session.selectList(map, page.row());
	}

	@Override
	public <T> List<T> findList(Map<String, ?> query) {
		return this.findList(query, null, null);
	}

	@Override
	public List<Entity> findList(Sort sort, Page page) {
		return this.findList(null, sort, page);
	}

	@Override
	public List<Entity> findList() {
		return this.findList(null, null);
	}

	@Override
	public int count(Map<String, ?> query) {
		return session.selectOne(query);
	}

	@Override
	public int count() {
		return session.selectOne();
	}

}
