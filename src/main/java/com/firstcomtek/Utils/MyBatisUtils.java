package com.firstcomtek.Utils;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtils {

	private static SqlSessionFactory factory=null;
	private static final ThreadLocal<SqlSession> threadLocal=new ThreadLocal<SqlSession>();
	
	static {
		try {
			InputStream is=Resources.getResourceAsStream("MyBatis.xml");
			factory=new SqlSessionFactoryBuilder().build(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSession getSession() {
		SqlSession session=threadLocal.get();
		if (session == null) {
			threadLocal.set(factory.openSession());
		}
		return threadLocal.get();
	}
	
	public static void closeSession() {
		SqlSession session=threadLocal.get();
		if (session != null) {
			session.close();
			threadLocal.remove();
		}
	}
	
	public static <T> T getMapper(Class<T> type){
		return getSession().getMapper(type);
	}
	
}
