package com.dnake.common;

import com.dnake.kit.DaoKit;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class TemplateSession {

	private SqlSession sqlSession;

	private String scanPackageName;

	private String packageName;

	public void setScanPackageName(String scanPackageName) {
		this.scanPackageName = scanPackageName;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	private String namespace() {
		System.err.println("namespace-----------------------------:\n" + packageName + "." + DaoKit.methodCaller(scanPackageName));
		return packageName + "." + DaoKit.methodCaller(scanPackageName);
	}

	public SqlSession session() {
		return sqlSession;
	}

	public <T> T selectOne() {
		return session().selectOne(namespace());
	}

	public <T> T selectOne(Object parameter) {
		return session().selectOne(namespace(), parameter);
	}

	public <E> List<E> selectList() {
		return session().selectList(namespace());
	}

	public <E> List<E> selectList(Object parameter) {
		return session().selectList(namespace(), parameter);
	}

	public <E> List<E> selectList(Object parameter, RowBounds rowBounds) {
		return session().selectList(namespace(), parameter, rowBounds);
	}

	public <K, V> Map<K, V> selectMap(String mapKey) {
		return session().selectMap(namespace(), mapKey);
	}

	public <K, V> Map<K, V> selectMap(Object parameter, String mapKey) {
		return session().selectMap(namespace(), parameter, mapKey);
	}

	public <K, V> Map<K, V> selectMap(Object parameter, String mapKey, RowBounds rowBounds) {
		return session().selectMap(namespace(), parameter, mapKey, rowBounds);
	}

	public int insert() {
		return session().insert(namespace());
	}

	public int insert(Object parameter) {
		return session().insert(namespace(), parameter);
	}

	public int update() {
		return session().update(namespace());
	}

	public int update(Object parameter) {
		return session().update(namespace(), parameter);
	}

	public int delete() {
		return session().delete(namespace());
	}

	public int delete(Object parameter) {
		return session().delete(namespace(), parameter);
	}

}
