package com.dnake.service;

import java.io.Serializable;
import java.util.Collection;

public interface CommonService<Entity, Id extends Serializable> {

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

}
