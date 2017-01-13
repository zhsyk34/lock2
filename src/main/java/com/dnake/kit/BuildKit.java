package com.dnake.kit;

import com.dnake.common.mapping.EntityManager;
import com.dnake.common.mapping.Tuple;
import com.dnake.entity.Lock;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BuildKit {

	public static void main(String[] args) throws Exception {
		build(Lock.class.getPackage(), true);
	}

	private static void build(Package pack, boolean overwrite) {
		List<Class<?>> list = FileKit.scanPackage(pack.getName());
		list.forEach(clazz -> build(clazz, overwrite));
	}

	private static void build(Class clazz, boolean overwrite) {
		buildFile(clazz, overwrite);
		buildXML(clazz, overwrite);
	}

	private static Path getPath(String dir) {
		return FileKit.getPath(FileKit.originalRoot(), dir);
	}

	private static void write(Path path, String str, boolean overwrite) {
		boolean r;
		if (Files.notExists(path)) {
			r = FileKit.createPath(path);
			System.out.println("create file :" + path + " : " + (r ? "success" : "fail"));
		} else {
			System.out.println(path + " is exist:");
			r = overwrite;
		}
		if (r) {
			FileKit.write(path, str);
		} else {
			System.out.println("scan ==> " + path);
		}
	}

	private static void buildFile(Class<?> clazz, boolean overwrite) {
		String entity = clazz.getSimpleName();

		String root = clazz.getPackage().getName();
		root = root.substring(0, root.lastIndexOf('.'));

		String dir = "java/" + root.replace('.', '/') + "/";
		System.out.println(dir);

		Path superDaoPath = getPath(dir + "dao/" + entity + "Dao.java");
		Path implDaoPath = getPath(dir + "dao/impl/" + entity + "DaoImpl.java");
		Path superServicePath = getPath(dir + "service/" + entity + "Service.java");
		Path implServicePath = getPath(dir + "service/impl/" + entity + "ServiceImpl.java");
		Path ctrlPath = getPath(dir + "controller/" + entity + "Controller.java");

		//superDao
		StringBuilder builder = new StringBuilder("package " + root + ".com.dnake.dao;\n\n");
		builder.append("import " + clazz.getName() + ";\n\n");
		builder.append("public interface " + entity + "Dao" + " extends CommonDao<" + entity + ", Long> {\n}");

		write(superDaoPath, builder.toString(), overwrite);

		//implDao
		builder = new StringBuilder();
		builder.append("package " + root + ".com.dnake.dao.impl;\n\n");
		builder.append("import " + clazz.getName() + ";\n");
		builder.append("import " + root + ".com.dnake.dao." + entity + "Dao;\n");
		builder.append("import org.springframework.stereotype.Repository;\n\n");
		builder.append("@Repository\n");
		builder.append("public class " + entity + "DaoImpl extends CommonDaoImpl<" + entity + ", Long>");
		builder.append(" implements ").append(entity + "Dao").append(" {\n}");

		write(implDaoPath, builder.toString(), overwrite);

		//superService
		builder = new StringBuilder("package " + root + ".com.dnake.service;\n\n");
		builder.append("import " + clazz.getName() + ";\n\n");
		builder.append("public interface " + entity + "Service extends CommonService<" + entity + ", Long> {\n}");

		write(superServicePath, builder.toString(), overwrite);

		//implService
		builder = new StringBuilder("package " + root + ".com.dnake.service.impl;\n\n");
		builder.append("import " + root + ".com.dnake.dao." + entity + "Dao;\n");
		builder.append("import " + root + ".com.dnake.service." + entity + "Service;\n");
		builder.append("import org.springframework.stereotype.Service;\n\n");
		builder.append("import javax.annotation.Resource;\n\n");
		builder.append("@Service\n");
		builder.append("public class " + entity + "ServiceImpl implements " + entity + "Service {\n");
		builder.append("\t@Resource\n");
		builder.append("\tprivate " + entity + "Dao " + StringKit.firstToLower(entity) + "Dao;\n");
		builder.append("}");

		write(implServicePath, builder.toString(), overwrite);

		//com.dnake.controller
		builder = new StringBuilder("package com.dnake.smart.com.dnake.controller;\n\n");
		builder.append("import org.springframework.stereotype.Controller;\n");
		builder.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		builder.append("\n");
		builder.append("@Controller\n");
		builder.append("@RequestMapping(\"/" + StringKit.firstToLower(entity) + "\")\n");
		builder.append("public class " + entity + "Controller extends CommonController {\n");
		builder.append("\t\n}");

		write(ctrlPath, builder.toString(), overwrite);

	}

	private static void buildXML(Class<?> clazz, boolean overwrite) {
		//设置路径
		final String dir = "resources/mapper/" + clazz.getSimpleName() + ".xml";
		Path path = getPath(dir);
		write(path, xmlContent(clazz), overwrite);
	}

	private static String xmlContent(Class<?> clazz) {
		String pre = "entity";
		EntityManager manager = EntityManager.from(clazz);
		if (manager == null) {
			throw new RuntimeException("clazz not mapping.");
		}

		//table
		String entity = manager.entity().getSimpleName(), table = manager.table();
		//id(com.dnake.entity),key(db)
		String id = manager.key().field(), key = manager.key().column();

		//columns
		String columns = manager.columns();

		//sql
		String saveValue = manager.save();
		String saveValues = manager.saves(pre);
		String updateValues = manager.update();

		//xmlMapping
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
				"<mapper namespace=\"" + clazz.getName() + "\">\n" +
				"    <sql id=\"table\">" + table + "</sql>\n" +
				"    <sql id=\"key\">" + key + "</sql>\n" +
				"    <sql id=\"id\">" + id + "</sql>\n" +
				"    <sql id=\"columns\">" + columns + "</sql>\n" +
				"    <sql id=\"sort\">\n" +
				"        <if test=\"sort != null and !sort.isEmpty() and order != null and !order.isEmpty()\">\n" +
				"            ORDER BY ${sort} ${order}\n" +
				"        </if>\n" +
				"    </sql>\n" +
				xmlMapping(manager) +
				"\n" +
				"    <insert id=\"save\" keyProperty=\"" + id + "\">\n" +
				"        INSERT INTO\n" +
				"        <include refid=\"table\"/>\n" +
				"        (<include refid=\"columns\"/>)\n" +
				"        VALUES\n" +
				"        (" + saveValue + ")\n" +
				"    </insert>\n" +
				"\n" +
				"    <insert id=\"saves\" keyProperty=\"" + id + "\">\n" +
				"        INSERT INTO\n" +
				"        <include refid=\"table\"/>\n" +
				"        (<include refid=\"columns\"/>)\n" +
				"        VALUES\n" +
				"        <foreach collection=\"list\" item=\"" + pre + "\" separator=\",\">\n" +
				"            (" + saveValues + ")\n" +
				"        </foreach>\n" +
				"    </insert>\n" +
				"\n" +
				"    <delete id=\"deleteById\">\n" +
				"        DELETE FROM\n" +
				"        <include refid=\"table\"/>\n" +
				"        WHERE\n" +
				"        <include refid=\"key\"/>\n" +
				"        = #{" + id + "}\n" +
				"    </delete>\n" +
				"\n" +
				"    <delete id=\"deleteByEntity\">\n" +
				"        DELETE FROM\n" +
				"        <include refid=\"table\"/>\n" +
				"        WHERE\n" +
				"        <include refid=\"key\"/>\n" +
				"        = #{" + id + "}\n" +
				"    </delete>\n" +
				"\n" +
				"    <delete id=\"deleteByIds\">\n" +
				"        DELETE FROM\n" +
				"        <include refid=\"table\"/>\n" +
				"        WHERE\n" +
				"        <include refid=\"key\"/>\n" +
				"        IN\n" +
				"        <foreach collection=\"array\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
				"            #{id}\n" +
				"        </foreach>\n" +
				"    </delete>\n" +
				"\n" +
				"    <delete id=\"deleteByEntities\">\n" +
				"        DELETE FROM\n" +
				"        <include refid=\"table\"/>\n" +
				"        WHERE\n" +
				"        <include refid=\"key\"/>\n" +
				"        IN\n" +
				"        <foreach collection=\"list\" item=\"" + pre + "\" open=\"(\" separator=\",\" close=\")\">\n" +
				"            #{" + pre + "." + id + "}\n" +
				"        </foreach>\n" +
				"    </delete>\n" +
				"\n" +
				"    <update id=\"update\">\n" +
				"        UPDATE\n" +
				"        <include refid=\"table\"/>\n" +
				"        SET\n" +
				"        " + updateValues + "\n" +
				"        WHERE\n" +
				"        <include refid=\"key\"/>\n" +
				"        = #{" + id + "}\n" +
				"    </update>\n" +
				"\n" +
				"    <select id=\"findById\" resultMap=\"" + entity + "\">\n" +
				"        SELECT * FROM\n" +
				"        <include refid=\"table\"/>\n" +
				"        WHERE\n" +
				"        <include refid=\"key\"/>\n" +
				"        = #{" + id + "}\n" +
				"    </select>\n" +
				"\n" +
				"    <select id=\"findUsedList\" resultMap=\"" + entity + "\">\n" +
				"        SELECT * FROM\n" +
				"        <include refid=\"table\"/>\n" +
				"        <include refid=\"search\"/>\n" +
				"        <include refid=\"sort\"/>\n" +
				"    </select>\n" +
				"\n" +
				"    <select id=\"countUsed\" resultType=\"int\">\n" +
				"        SELECT COUNT(*) FROM\n" +
				"        <include refid=\"table\"/>\n" +
				"        <include refid=\"search\"/>\n" +
				"    </select>\n" +
				"\n" +
				"    <sql id=\"search\">\n" +
				"    </sql>\n" +
				"\n" +
				"</mapper>";
	}

	private static String xmlMapping(EntityManager manager) {
		if (manager == null) {
			return null;
		}
		Class entity = manager.entity();
		List<Tuple> bases = manager.getBases();
		Tuple key = manager.key();
		StringBuilder builder = new StringBuilder("    <resultMap id=\"" + entity.getSimpleName() + "\" type=\"" + entity.getSimpleName() + "\">\n");

		bases.forEach(tuple -> {
			if (tuple == key) {
				builder.append("        <id property=\"" + key.field() + "\" column=\"" + key.column() + "\"/>\n");
			} else {
				builder.append("        <result property=\"" + tuple.field() + "\" column=\"" + tuple.column() + "\"/>\n");
			}
		});
		builder.append("    </resultMap>\n");

		return builder.toString();
	}
}
