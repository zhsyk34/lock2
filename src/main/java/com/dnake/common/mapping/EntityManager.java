package com.dnake.common.mapping;

import com.dnake.common.annotation.Column;
import com.dnake.common.annotation.Id;
import com.dnake.common.annotation.Table;
import com.dnake.kit.StringKit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityManager<T> {
	private Class<T> entity;
	private String table;

	private Tuple key;
	private List<Tuple> bases;

	public static <T> EntityManager from(Class<T> clazz) {
		EntityManager entity = new EntityManager();
		entity.entity = clazz;

		String tableValue = StringKit.firstToLower(clazz.getSimpleName());
		Table table = clazz.getAnnotation(Table.class);
		if (table != null && !table.value().isEmpty()) {
			tableValue = table.value();
		}
		entity.table = tableValue;

		List<Tuple> list = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();

			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				String idValue = id.value();
				if (idValue.isEmpty()) {
					idValue = fieldName;
				}
				Tuple tuple = Tuple.of(fieldName, idValue);
				entity.key = tuple;
				list.add(tuple);
				continue;
			}

			String columnValue = fieldName;
			Column column = field.getAnnotation(Column.class);
			if (column != null && !column.value().isEmpty()) {
				columnValue = column.value();
			}
			list.add(Tuple.of(fieldName, columnValue));
		}
		if (entity.key == null) {
			throw new RuntimeException("id not found.");
		}
		entity.bases = list;

		return entity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("com.dnake.entity = ").append(entity.getName()).append("\n");
		builder.append("table = ").append(table).append("\n");

		bases.forEach(tuple -> {
			if (tuple == key) {
				builder.append("id : ").append(tuple).append("\n");
			} else {
				builder.append("column : ").append(tuple).append("\n");
			}
		});
		return builder.toString();
	}

	public Class<T> entity() {
		return entity;
	}

	public String table() {
		return table;
	}

	public Tuple key() {
		return key;
	}

	public List<Tuple> getBases() {
		return bases;
	}

	public String columns() {
		return StringKit.from(columnList());
	}

	public List<String> columnList() {
		List<String> list = new ArrayList<>();
		bases.forEach(tuple -> list.add(tuple.column()));
		return list;
	}

	public String update() {
		List<String> list = new ArrayList<>();
		bases.forEach(tuple -> {
			if (tuple != key) {
				//skip id
				list.add(tuple.column() + " = #{" + tuple.field() + "}");
			}
		});
		return StringKit.from(list);
	}

	public String save() {
		return saves(null);
	}

	public String saves(String pre) {
		List<String> list = new ArrayList<>();
		bases.forEach(tuple -> {
			if (tuple == key) {
				list.add("NULL");
			} else if (pre == null || pre.isEmpty()) {
				String s = "#{" + tuple.field() + "}";
				list.add(s);
			} else {
				list.add("#{" + pre + "." + tuple.field() + "}");
			}

		});
		return StringKit.from(list);
	}
}
