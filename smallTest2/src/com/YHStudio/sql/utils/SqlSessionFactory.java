package com.YHStudio.sql.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘杰
 * @version 1.0 2018年9月7日 
 * 类的作用:动态提供实现工厂
 */
public class SqlSessionFactory {

	// 存储接口名称
	public static String interfaceName;

	// 获取增强委托类的实例
	public static Object getSession(Class<?> clazz) {
		// 获取接口运行时类对象
		SqlSessionFactory.interfaceName = clazz.getName();
		return new invoker().getInstance(clazz);
	}
}

class invoker {

	public Object getInstance(Class<?> clazz) {// 返回代理对象
		MethodProxy invocationHandler = new MethodProxy();
		// 将方法执行权交给MethodProxy类中的invoke方法
		Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, invocationHandler);
		return (Object) obj;
	}

}

class MethodProxy implements InvocationHandler {// 代理类

	private static Connection conn;// 数据库连接
	private static PreparedStatement ps;// PreparedStatement实例
	private static ParseFileUtils pfu = new ParseFileUtils();// ParseFileUtils实例

	@Override
	public Object invoke(Object proxy, Method method, Object[] arg2) throws Throwable {
		// 判断传进来的是实现类还是接口
		if (Object.class.equals(method.getDeclaringClass())) {// 实现类
			try {
				return method.invoke(this, arg2);// 执行方法
			} catch (Exception error) {
				error.printStackTrace();
			}
		} else {// 接口
			return run(method, arg2);
		}
		return null;
	}
	
	/**
	 * 反射接口的实现类
	 * @param method - 接收一个Method对象
	 * @param arg - 接收一个Method的参数对象
	 * @return - 返回具体的sql执行结果
	 * @throws Exception
	 */
	private Object run(Method method, Object[] arg) throws Exception {
		String sql = "";// 定义sql参数字符串
		// 连接数据库
		connectDataBase();
		// 获取命名空间、参数类型、结果集类型、mpper中sql语句等数据
		Map<String, String> map = getData(method.getName());
		// 检测最开始反射接口名称与mapper文件命名空间的接口名称是否对应(安全检查)
		if (map.get("nameSpace").equals(SqlSessionFactory.interfaceName)) {
			// 获取接口方法中的形参的类型
			String typeName = method.getParameterTypes()[0].getTypeName();
			// 检测最开始反射的接口中的指定的调用方法的形参的类型与mapper文件中的指定分组中的id是否对应(安全检查)
			if (map.get("paramType").equals(typeName)) {
				// 取出sql语句
				sql = map.get("sql");
				Class<?> clazz = null;
				// 获取最开始反射接口的指定的调用方法的形参中的参数对象的类型,并创建其运行时类对象
				clazz = Class.forName(method.getParameterTypes()[0].getTypeName());
				// 获取属性集合
				Field[] fields = clazz.getDeclaredFields();
				// 定义正则表达式
				Pattern ptn = Pattern.compile("[${]+\\w+}");
				Matcher mth = ptn.matcher(map.get("sql"));
				// 定义List集合用来存储sql语句中的${...}
				List<String> list = new ArrayList<String>();
				while (mth.find()) {
					list.add(mth.group());
				}
				// 遍历 -> 属性集合与正则表达式匹配的结果
				/**
				 * 仅仅对于添加数据、更新数据使用,删除数据与查询数据此方式不支持!
				 */
				System.out.println(list.size());
				System.out.println(fields.length);
				if (list.size() != 0) {
					try {
						if (true) {
							// 定义List<String>集合存储参数类型
							List<String> dataType = new ArrayList<String>();

							for (int o = 0; o < list.size(); o++) {
								// 将insert ... ${ Xxx } 替换成 insert ... ${ ?,?,? }形式
								sql = sql.replace(list.get(o), "?");// 获取返回值
							}
							// 获取prepareStatement类的实例
							ps = conn.prepareStatement(sql);
							System.out.println(ps);
							// 遍历
							for (int i = 0; i < list.size(); i++) {
								// 定义临时变量存储拼接成${ ... }后的属性字段
								String stemp = "${" + fields[i].getName() + "}";
								// 检测拼接后的属性字段与list集合存储的mapper文件中的${ ... }等字段是否匹配(安全检查)
								if (stemp.equals(list.get(i))) {// 如果匹配进行赋值操作
									// 获取属性集合中所有属性的数据类型
									String type = fields[i].getType().toString();
									// 格式化数据类型字符串并将其封装进名为dataType的List集合中
									if (type.indexOf(".") != -1) {
										dataType.add(type.substring(type.lastIndexOf(".") + 1, type.length()));
									} else {
										dataType.add(type);
									}
									ps = paramFill(dataType, fields, clazz, arg, ps);
								}
							}
						}
						return ps.executeUpdate();// 返回执行的结果
					} catch (Exception error) {
						return ps.executeQuery();// 返回执行的结果
					}
				} else {
					ps = conn.prepareStatement(sql);
					System.out.println("not ok ...");
					return ps.executeQuery();// 返回执行的结果
				}
			}
		} else {
			throw new Error("Cause by : Namespace names do not match!");
		}
		System.out.println(ps);
		System.out.println("到这了 ...");
		return ps.executeUpdate();// 返回执行的结果
	}

	/**
	 * @param dataType - 接收一个从属性集合中提取的参数类型的的List集合
	 * @param fields - 接收一个属性集合
	 * @param clazz - 接收一个运行时类对象
	 * @param arg - 接收一个Method的参数对象
	 * @param ps - 接收一个PreparedStatement对象
	 * @return - 返回一个PreparedStatement对象
	 */
	private PreparedStatement paramFill(List<String> dataType, Field[] fields, Class<?> clazz, Object[] arg,
			PreparedStatement ps) {
		for (int k = 0; k < dataType.size(); k++) {// 进行参数类型匹配向sql语句中填充参数值
			// 此处应该获取拿到属性拼接成get方法 ...
			String field = fields[k].getName();// 获取属性名
			// 拼接各自对应的get方法
			String methods = "get" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
			// 获得get方法的引用
			Method method2;
			try {
				// 拿到指定运行时类中的所有get方法
				method2 = clazz.getMethod(methods);
				if ("String".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为String类型
					ps.setString(k + 1, method2.invoke(arg[0]).toString());// 向sql中填充值
				} else if ("int".equals(dataType.get(k)) || "Integer".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为int或Integer类型
					ps.setInt(k + 1, Integer.valueOf(method2.invoke(arg[0]).toString()));
				} else if ("boolean".equals(dataType.get(k)) || "Boolean".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为boolean或Boolean类型
					ps.setBoolean(k + 1, Boolean.valueOf(method2.invoke(arg[0]).toString()));
				} else if ("short".equals(dataType.get(k)) || "Short".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为short或Short类型
					ps.setShort(k + 1, Short.valueOf(method2.invoke(arg[0]).toString()));
				} else if ("float".equals(dataType.get(k)) || "Float".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为float或Float类型
					ps.setFloat(k + 1, Float.valueOf(method2.invoke(arg[0]).toString()));
				} else if ("double".equals(dataType.get(k)) || "Double".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为double或Double类型
					ps.setDouble(k + 1, Double.valueOf(method2.invoke(arg[0]).toString()));
				} else if ("Date".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为java.sql.Date类型
					ps.setDate(k + 1, java.sql.Date.valueOf(method2.invoke(arg[0]).toString()));
				} else if ("Time".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为java.sql.Time类型
					ps.setTime(k + 1, java.sql.Time.valueOf(method2.invoke(arg[0]).toString()));
				} else if ("Timestamp".equals(dataType.get(k))) {// 如果mapper文件中当前字段的数据类型为java.sql.Timestamp类型
					ps.setTimestamp(k + 1, java.sql.Timestamp.valueOf(method2.invoke(arg[0]).toString()));
				} else {
					// 抛出异常 ...
					throw new Error("Cause by : Parameter type conversion failed!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ps;
	}

	/**
	 * 获取命名空间、参数类型、结果集类型、mpper中sql语句等数据
	 * @param methodName - 接收一个方法名称
	 * @return - 返回一个存储数据的map集合
	 */
	private Map<String, String> getData(String methodName) {
		return pfu.parseMapperFile(methodName);// 解析mapper文件
	}

	/**
	 * 解析配置文件(ConfigFile)
	 * @return - 返回一个存储数据库连接参数的map集合
	 */
	private Map<String, String> parseConfigFile() {
		return pfu.parseConfigFile();// 解析config配置文件
	}
	/**
	 * 连接数据库
	 */
	private void connectDataBase() {
		// 解析配置文件
		Map<String, String> map = parseConfigFile();
		try {
			// 数据库驱动
			Class.forName(map.get("sqlDriver"));
			// 与数据库建立连接
			conn = DriverManager.getConnection(map.get("sqlUrl"), map.get("useName"), map.get("usePwd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
