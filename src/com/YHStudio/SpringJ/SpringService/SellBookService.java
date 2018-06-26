package com.YHStudio.SpringJ.SpringService;

/**
 * @author 刘杰
 * @version 1.0
 * 2018年6月25日
 * 类的作用:模拟买书的service类 
 */
public class SellBookService {

	// 声明成员属性
	private String name;
	private int address;
	private BuyBookService buyBook;
	
	// 创建无参构造函数
	public SellBookService() {
		super();
	}
	// 创建有参构造函数
	public SellBookService(String name,int address, BuyBookService buyBook) {
		this.name = name;
		this.address = address;
		this.buyBook = buyBook;
	}
	// 创建对应的setter与getter方法
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setBuyBook(BuyBookService buyBook) {
		this.buyBook = buyBook;
	}
	public BuyBookService getBuyBook() {
		return buyBook;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "SellBookService [name=" + name + ", address=" + address + ", buyBook=" + buyBook + "]";
	}
	
	public void testInjection() {
		System.out.println("奇迹将现 ...");
		buyBook.see();
	}
}
