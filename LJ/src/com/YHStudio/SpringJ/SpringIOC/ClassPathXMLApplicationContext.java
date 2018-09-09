package com.YHStudio.SpringJ.SpringIOC;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘杰
 * @version 1.0 2018年6月25日 类的作用:创建IOC容器
 */
public class ClassPathXMLApplicationContext implements ApplicationContext {

	// 声明根路径
	private String rootPath;
	// 声明配置文件的名字
	private String configurationFile;
	// 声明读取配置文件的结果
	private String readResult;
	// 声明用来连接每个正则匹配结果连接字符串
	private String connect = "";
	
	// 创建List集合来存储目标元素(没有属性引入的)标签的集合
	List<String> data = new ArrayList<String>();
	// 创建List集合来存储目标元素(有属性引入的)标签的集合
	List<String> item = new ArrayList<String>();
	// 创建List集合来存储基本类型包括String型的属性名
	List<String> fieldItem = new ArrayList<String>();
	// 初始化Map集合用来存储Bean
	Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * 无参构造器
	 */
	public ClassPathXMLApplicationContext() {
		super();
	}

	/**
	 * 有参构造器
	 * @param configurationFile => 接收一个配置文件路径
	 * 在这里初始化IOC容器并完成对Bean的装配
	 */
	public ClassPathXMLApplicationContext(String configurationFile) {
		this.configurationFile = configurationFile;
		// 通过centralWork来进行对IOC容器中Bean的装配
		centralWork();
	}
	
	/**
	 * 获取Bean的方法
	 */
	@Override
	public Object getBean(String beanName) {
		/**
		 * 1.拼接配置文件的路径 2.通过IO流读取配置文件 3.解析配置文件(获取到id、全类名、属性名、属性值)
		 * 4.通过全类名用反射策略实例化该类对象 ... 获取属性设置属性值 ...
		 */
		return map.get(beanName);
	}

	/**
	 * 装配Bean
	 */
	private void centralWork() {
		// 读取配置文件
		readConfigUrationFile();
		// 解析配置文件
		parseConfigUrationFile();
	}

	/**
	 * 读取配置文件
	 */
	public void readConfigUrationFile() {
		// 获取项目根路径
		rootPath = System.getProperty("user.dir");
		// 截取项目名
		rootPath = rootPath.substring(rootPath.indexOf("\\") + 1, rootPath.length());
		// 拼接配置文件在当前项目工程中的路径
		configurationFile = "/" + rootPath + "/src/" + configurationFile;
		// 创建一个File对象(对应配置文件)
		File file = new File(configurationFile);
		// 判断此文件是否存在
		if (file.exists()) {// 如果存在
			
			FileInputStream fis;// 获取文件输入流
			BufferedInputStream bis = null;// 获取文件输入流
			ByteArrayOutputStream baos = null;// 获取ByteArrayOutputStream输出流
			try {
				fis = new FileInputStream(file);
				// 获取缓冲流
				bis = new BufferedInputStream(fis);
				// 获取ByteArrayStream
				baos = new ByteArrayOutputStream();
				// 获取判断当前读取状态的阈值
				int len = 0;
				// 获取临时存储字节的数组
				byte[] storage = new byte[1024];
				// 边读边写
				while ((len = bis.read(storage)) != -1) {
					baos.write(storage, 0, len);
				}
				// 将流转换为字符串
				readResult = baos.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭流
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (baos != null) {
							try {
								baos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} else {
			throw new Error("Cause by : The specified file can not be found in the specified directory!");
		}
	}

	/**
	 * 解析配置文件
	 */
	public void parseConfigUrationFile() {
		// 先检索<bean id="" class="" />格式的标签(没有property属性的标签)
		// 定义相关正则
		Pattern ptn_1 = Pattern.compile(
				"<bean\\sid=\"[a-zA-Z][a-zA-Z0-9]+\"\\s(class=\"[a-zA-Z][a-zA-Z0-9.]+\"\\s/>|class=\"[a-zA-Z][a-zA-Z0-9.]+\"/>)");
		Matcher mch_1 = ptn_1.matcher(readResult);
		while (mch_1.find()) {
			data.add(mch_1.group());// 将符合条件的标签添加进data(List)集合中
		}
		int singleCount = data.size();// 获取data(List)集合的长度
		if(singleCount != 0) {// 当xml中有没有property属性标签的bean标签的时候
			for(int i = 0;i < singleCount;i++) {
				createSingleBean(data.get(i));// 创建该Bean
			}
		}
		
		// 再检索<bean id="" class=""> ... </bean>格式的标签
		Pattern ptn_2 = Pattern.compile("<bean\\sid=\"[a-zA-Z][a-zA-Z0-9]+\"\\sclass=\"[a-zA-Z][a-zA-Z0-9.]+\">[\\s\\S]+</bean>");
		Matcher mch_2 = ptn_2.matcher(readResult);
		while(mch_2.find()) {
			connect += mch_2.group();// 将我们匹配到的结果拼接起来
		}
		
		// 以</bean>为条件分割字符串
		String[] split = connect.split("</bean>");
		// 通过分割完成的长度来判断执行的次数
		for(int j = 0;j < split.length;j++) {
			createPropertyBean(split[j]);
		}
		
	}

	/**
	 * 创建没有property属性的bean标签的bean,并将其放入IOC容器(map集合)中
	 * @param tag
	 */
	public void createSingleBean(String tag) {
		// 获取id
		String id = tag.substring(tag.indexOf("\"") + 1, tag.indexOf("class=") - 2);
		// 获取clazz
		String sclass = tag.substring(tag.lastIndexOf("=") + 2, tag.lastIndexOf("\""));
		try {
			// 获取指定类型的运行时类
			Class<?> clazz = Class.forName(sclass);
			// 通过反射机制来创建对象
			Object obj = clazz.newInstance();
			// 将对象装进Map集合中
			map.put(id, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建有property属性的bean标签的bean,并将其放入IOC容器(map集合)中
	 * @param beanStr
	 */
	public void createPropertyBean(String beanStr) {
		Pattern ptn_3 = Pattern.compile("<bean\\sid=\"[a-zA-Z][a-zA-Z0-9]+\"\\sclass=\"[a-zA-Z][a-zA-Z0-9.]+\">");
		Matcher mch_3 = ptn_3.matcher(beanStr.trim());
		String tagHeader = "";
		while(mch_3.find()) {
			tagHeader = mch_3.group();
		}
		// 拿到id
		String id= tagHeader.substring(tagHeader.indexOf("\"") + 1, tagHeader.indexOf("class=") - 2);
		// 拿到全类名class
		String classes = tagHeader.substring(tagHeader.indexOf("class=\"") + 7, tagHeader.lastIndexOf("\""));
		
		Object obj = null;
		Class<?> clazz = null;
		try {
			// 获取对应的运行时类
			clazz = Class.forName(classes);
			// 通过反射机制来创建对象
			obj = clazz.newInstance();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取对应类中的所有属性,并将其属性名存储进List集合中
		Field[] fields = clazz.getDeclaredFields();
		// 获取该类中的所有的属性名
		for(int i = 0;i < fields.length;i++) {
			fieldItem.add(fields[i].getName());
		}
		// 获取到<property ...>带有value的
		Pattern ptn_4 = Pattern.compile("<property\\sname=\"[a-zA-Z][a-zA-Z0-9]+\"\\s((value=\"[a-zA-Z0-9][a-zA-Z0-9]+\"\\s/|value=\"[a-zA-Z0-9][a-zA-Z0-9]+\"/)>|value=\"[a-zA-Z0-9][a-zA-Z0-9]+\"></property>)");
		Matcher mch_4 = ptn_4.matcher(beanStr.trim());
		String propertyStr = "";
		while(mch_4.find()) {
			
			propertyStr = mch_4.group();
			// 如果该Bean的属性中没有value = ""我们就不执行以下操作
			 if("".equals(propertyStr)) {
				
			 }else {
				
				// 取到(value)中的name
				 String name = propertyStr.substring(propertyStr.indexOf("\"") + 1, propertyStr.indexOf("value=") - 2);
				// 取到value中指定属性的值
				 String value = propertyStr.substring(propertyStr.lastIndexOf("=\"") + 2, propertyStr.lastIndexOf("\""));
				// 到List集合中查找对应的属性名
				 int index = fieldItem.indexOf(name);
				 if(index != -1) {// 如果找到
					 
					 Field property = fields[index];// 就通过上面的索引来获取到该属性
					 property.setAccessible(true);// 设置访问权限为最高
					 Class<?> type = property.getType();// 获取该属性的数据类型
					switch(type.toString()) {
					case "class java.lang.String":
						String arg_String = String.valueOf(value);
						try {
							property.set(obj, arg_String);
						} catch (Exception e) {
							e.printStackTrace();
						}
					continue;	
					case "int":
						int arg_int = Integer.valueOf(value).intValue();
						try {
							property.set(obj, arg_int);
						} catch (Exception e) {
							e.printStackTrace();
						}	
					continue;
					case "float":
						float arg_float = Float.valueOf(value).floatValue();
						try {
							property.set(obj, arg_float);
						} catch (Exception e) {
							e.printStackTrace();
						}
					continue;
					case "double":
						double arg_double = Double.valueOf(value).doubleValue();
						try {
							property.set(obj, arg_double);
						} catch (Exception e) {
							e.printStackTrace();
						}
					continue;
					case "boolean":
						boolean arg_boolean = Boolean.valueOf(value).booleanValue();
						try {
							property.set(obj, arg_boolean);
						} catch (Exception e) {
							e.printStackTrace();
						}
					continue;
					}
					
					
				 }
			}
		}
		// 获取到<property ...>带有ref的
		Pattern ptn_5 = Pattern.compile("<property\\sname=\"[a-zA-Z][a-zA-Z0-9]+\"\\s((ref=\"[a-zA-Z][a-zA-Z0-9]+\"\\s/|ref=\"[a-zA-Z][a-zA-Z0-9]+\"/)>|ref=\"[a-zA-Z][a-zA-Z0-9]+\"></property>)");
		Matcher mch_5 = ptn_5.matcher(beanStr.trim());
		String refName = "";
		@SuppressWarnings("unused")
		String refValue = "";
		while(mch_5.find()) {
			// 拿到属性名
			refName = mch_5.group().substring(mch_5.group().indexOf("name=\"") + 6, mch_5.group().indexOf("ref=") - 2);
			
			// 拿到属性值
			refValue = mch_5.group().substring(mch_5.group().lastIndexOf("=") + 2, mch_5.group().lastIndexOf("\""));
			
			// 根据属性名去map集合中找到对应的对象
			Object obj2 = map.get(refName);
			// 去fieldItem的List集合中找到对应的属性名的索引
			int index = fieldItem.indexOf(refName);
			if(index != -1) {
				Field property = fields[index];
				property.setAccessible(true);
				try {
					property.set(obj, obj2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		map.put(id, obj);
		// 清除List中的元素
		fieldItem.clear();
	}
	
}
