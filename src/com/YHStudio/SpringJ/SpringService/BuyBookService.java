package com.YHStudio.SpringJ.SpringService;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年6月25日
 * 类的作用:模拟买书的Service
 */
public class BuyBookService {

	private String name;
	// 创建无参构造函数
	public BuyBookService() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 创建see方法
	public void see() {
		System.out.println("see() ...");
	}
}
