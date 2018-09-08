package com.YHStudio.sql.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年9月7日
 * 类的作用:加载配置文件
 */
public class LoaderConfFile {
	
	/**
	 * 加载配置文件
	 * @param filePath - 接收一个配置文件路径
	 * @return - 返回一个内存中的内容
	 */
	public static String load(String filePath) {
		return read(filePath);
	}
	
	/**
	 * 读取配置文件
	 * @param filePath - 接收一个文件路径
	 * @return - 返回读取完毕的内存中的信息字符串
	 */
	private static String read(String filePath) {
		// 创建File对象
		File readfile = new File(filePath);
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		String str = "";
		try {
			// 获取字节流FileInputStream实例
			FileInputStream fis = new FileInputStream(readfile);
			// 获取缓冲流BufferedInputStream
			bis = new BufferedInputStream(fis);
			// 获取字节数组输出流
			baos = new ByteArrayOutputStream();
			// 定义临时存储字节的数组
			byte[] storage = new byte[1024];
			// 定义判断读取状态的标志
			int len = 0;
			// 边读边写
			while((len = bis.read(storage)) != -1) {
				baos.write(storage, 0, len);
			}
			str = baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(bis != null) {
				try {
					bis.close();// 关闭流
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					if(baos != null) {
						try {
							baos.close();// 关闭流
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return str;
	}
	
}
