package com.YHStudio.sql.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘杰
 * @version 1.0 2018年9月7日 
 * 类的作用:解析文件
 */
public class ParseFileUtils implements SqlOperation {

	private static String mapperFilePath;

	@Override
	/**
	 * 解析ConfigFile配置文件
	 * @return - 返回一个存放数据的map集合
	 */
	public Map<String, String> parseConfigFile() {
		// 加载配置文件
		String source = LoaderConfFile.load("/smallTest2/config/jBatis-config");
		// 解析配置文件(获取sqlDriver值)
		String DRIVER = source.substring(source.indexOf("sqlDriver =") + 11, source.indexOf("\n"));

		// 重置source串
		source = source.replace("sqlDriver =" + DRIVER, "");

		// 获取sqlDriver值
		DRIVER = DRIVER.trim();// 拿到驱动值

		String SQLURL = source.substring(source.indexOf("sqlUrl =") + 8, source.indexOf("\r"));

		// 重置source串
		source = source.replace("sqlUrl =" + SQLURL, "");
		source = source.substring(source.indexOf("useName ="), source.length());
		// 获取sqlUrl值
		SQLURL = SQLURL.trim();// 拿到路径值

		String USERNAME = source.substring(source.indexOf("useName =") + 9, source.indexOf("\n"));

		// 重置sorce
		source = source.substring(source.indexOf("usePwd ="), source.length());
		USERNAME = USERNAME.trim();// 拿到用户名

		String USERPWD = source.substring(source.indexOf("usePwd =") + 8, source.indexOf("\n"));

		USERPWD = USERPWD.trim();// 拿到密码
		// 拿到mapper.jtml文件的路径
		mapperFilePath = source.substring(source.lastIndexOf("href=\"") + 6, source.lastIndexOf("\""));
		Map<String, String> map = new HashMap<String, String>();
		map.put("sqlDriver", DRIVER);
		map.put("sqlUrl", SQLURL);
		map.put("useName", USERNAME);
		map.put("usePwd", USERPWD);
		return map;
	}

	@Override
	/**
	 * 解析mapper文件并将数据封装成Map集合返回
	 */
	public Map<String, String> parseMapperFile(String methodName) {
		// 加载mapper.jdtl配置文件
		String source = LoaderConfFile.load(mapperFilePath);
		// 通过split将字符串进行分割
		String[] split = source.split("&");
		// 定义Map集合用来存储数据
		Map<String, String> map = new HashMap<String, String>();
		String str = "";
		for(int i = 0;i < split.length;i++) {
			if(i == 0) {// 拿到命名空间字段值并将其封装进map集合
				String nameSpace = split[i].substring(split[i].indexOf("{") + 1, split[i].length()).trim();
				map.put(nameSpace.split(":")[0].trim(), nameSpace.split(":")[1].trim());
			}
			// 拿方法名称去split数组中去寻找id:Xxx的一条记录
			if(queryData(methodName, split[i])) {	
				str = split[i].trim();
				str = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
				String[] split2 = str.split("\\|");
				for(int j = 0;j < split2.length;j++) {// 拿到数据段儿值
					map.put(split2[j].split(":")[0].trim(), split2[j].split(":")[1].trim());
				}
			}
		}
		return map;// 返回数据
	}
	
	// 创建查找sql语句的方法
	private boolean queryData(String methodName, String source) {
		Pattern ptn = Pattern.compile("id" + "\\s{0,1}:\\s{0,1}" + methodName);
		Matcher mth = ptn.matcher(source);
		boolean tag = false;
		while(mth.find()) {
			tag = true;
		}
		return tag;
	}
}
