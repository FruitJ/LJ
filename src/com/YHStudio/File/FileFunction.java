package com.YHStudio.File;

import java.io.File;

import org.junit.Test;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年6月25日
 * 类的作用:关于文件的一些方法
 */
public class FileFunction {
	private File file = new File("C:\\Users\\Lenovo\\Desktop\\新建文本文档 (5).txt");
	private File file1 = new File("/file1.txt");
	private File file2 = new File("C:\\Users\\Lenovo\\Desktop\\新建文本文档 (100).txt");
	private File file3 = new File("C:\\Users\\Lenovo\\Desktop\\新建文本文档 (102).png");
	private File file4 = new File("C:\\Users\\Lenovo\\Desktop\\flightVisualization (1)");
	private File file5 = new File("C:\\Users\\Lenovo\\Desktop\\flight.txt");
	
	
	@Test
	public void test1() throws Exception {
		// getName()获取文件名
		String fileName = file.getName();
		System.out.println(fileName);// 新建文本文档 (5).txt
		
		// getPath()获取文件路径
		String filePath = file.getPath();
		System.out.println(filePath);// C:\Users\Lenovo\Desktop\新建文本文档 (5).txt
		
		// getAbsolutePath()获取文件绝对路径
		String fileAbsolutePath = file.getAbsolutePath();
		System.out.println(fileAbsolutePath);// C:\Users\Lenovo\Desktop\新建文本文档 (5).txt
	
		// getAbsolutePathFile获取绝对路径文件名
		String absoluteFile = file.getAbsolutePath();
		System.out.println(absoluteFile);// C:\Users\Lenovo\Desktop\新建文本文档 (5).txt
	
		// getParent()获取指定文件路径中的上一级目录
		String parent = file.getParent();
		System.out.println(parent);// C:\Users\Lenovo\Desktop
		
		// file.renameTo(File1的实例)更改file文件的名字为file1中指定的名字
		boolean isOk = file3.renameTo(file2);
		System.out.println("更改文件名:" + isOk);
		
		// createNewFile()创建新文件
		boolean isCreateNewFile = file3.createNewFile();
		System.out.println("创建新的文件:" + isCreateNewFile);
		
		// delete()删除指定目录的文件
		boolean isDeleteFile = file3.delete();
		System.out.println("删除文件:" + isDeleteFile);
		
		// 判断指定目录的文件是否存在
		boolean isAlive = file2.exists();
		System.out.println("file2文件存在:" + isAlive);
		
		// canWrite()判断文件是否可以写入、canRead()判断文件是否能被读取
		boolean isWrite = file2.canWrite();
		boolean isRead = file2.canRead();
		System.out.println("file2文件能读:" + isRead + ",file2文件能被写:" + isWrite);
	
		// isFile()判断Xxx是否是一个文件
		boolean isFile = file2.isFile();
		System.out.println("file2是文件:" + isFile);
		
		// isDirectory()判断Xxx是否是一个文件夹
		boolean isDirectory = file2.isDirectory();
		System.out.println("file2是一个文件夹:" + isDirectory);
		boolean isDirectory1 = file4.isDirectory();
		System.out.println("file4是一个文件夹:" + isDirectory1);
	
		// mkdir()创建一个文件夹
		boolean mkdir = file5.mkdir();
		System.out.println("file5文件被创建:" + mkdir);
		
		// list()获取文件夹内Xxx个数
		String[] list = file4.list();
		System.out.println("文件夹内部长度为:" + list.length);
		
		// listFiles()获取文件夹内的文件个数
		File[] listFiles = file4.listFiles();
		System.out.println(listFiles);
		
		// 
		long length = file4.length();
		System.out.println("file文件中的内容长度为:" + length);// file文件中的内容长度为:4096
	}
	// 上面两个获取的路径看似相同,但是请看下面,将文件的位置设置为当前工程中的根目录下
	@Test
	public void test2() {
		String filePath = file1.getPath();
		System.out.println(filePath);// \file1.txt
		
		String fileAbsolutePath = file1.getAbsolutePath();
		System.out.println(fileAbsolutePath);// D:\file1.txt
		
		String absoluteFile = file1.getAbsolutePath();
		System.out.println(absoluteFile);// D:\file1.txt

		String parent = file1.getParent();
		System.out.println(parent);// \
		
		
	}

}
