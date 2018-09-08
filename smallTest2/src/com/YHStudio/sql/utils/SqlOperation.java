package com.YHStudio.sql.utils;

import java.util.Map;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年9月7日
 * 类的作用:连接数据库
 */
public interface SqlOperation {

	/**
	 * 解析jBatis配置文件
	 * @return - 返回一个返回一个存放config配文件中数据的map集合
	 */
	public abstract Map<String, String> parseConfigFile();
	
	/**
	 * 解析mapper.jdtl mapper文件
	 * @param MethodName - 接收一个反射的接口中的方法名称
	 * @return - 返回一个存放mapper中数据的map集合
	 */
	public abstract Map<String, String> parseMapperFile(String MethodName); 
}
