package com.YHStudio.SpringJ.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年6月26日
 * 类的作用:测试List集合删除元素
 */
public class TestList {

	public static void main(String[] args) {
		List<String> data = new ArrayList<String>();
		data.add("123");
		data.add("456");
		data.add("789");
		data.clear();
		System.out.println(data.size());
	}
}
